package main.service;

import main.dto.UserDto;
import main.entity.Audit;
import main.entity.RoleType;
import main.entity.StatusType;
import main.entity.User;
import main.exception.AlreadyOnDbException;
import main.exception.NotFoundException;
import main.mapper.UserMapper;
import main.repository.AuditRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto addUser(UserDto userDto) {
        Optional<User> existingUsername = userRepository.findByUsername(userDto.getUsername());
        existingUsername.ifPresent(e -> {
            throw new AlreadyOnDbException("An user with same username " + userDto.getUsername() + " already exists");
        } );
        this.auditRepository.save(new Audit("addUser", new Timestamp(System.currentTimeMillis()) ));
        //return userRepository.save(userDto);
        return userMapper.mapToUserDto(userRepository.save(userMapper.mapToUser(userDto)));
    }

    public List<UserDto> getUsers(){
        List<User> users = userRepository.findAll();
        this.auditRepository.save(new Audit("getAll", new Timestamp(System.currentTimeMillis()) ));
        return users.stream()
                .map(user -> userMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NotFoundException("User with id " + id + "attempted to be retrieved");
        }
        this.auditRepository.save(new Audit("getById", new Timestamp(System.currentTimeMillis()) ));
        return Optional.of(userMapper.mapToUserDto(user.get()));
    }

    public List<UserDto> getUserByRoleAndStatus(RoleType role, StatusType status) {
        List<User> users = userRepository.findByRoleOrStatus(role, status);
        if(users.isEmpty()) {
            throw new NotFoundException(String.format("No user with role: %s was found", role));
        }
        this.auditRepository.save(new Audit("getRoleAndStatus", new Timestamp(System.currentTimeMillis()) ));
        return users.stream()
                .map(user -> userMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }


//    public UserDto getUserByUsername(String username) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isEmpty()) {
//            throw new NotFoundException(String.format("No user with username: %s was found", username));
//        }
//        return userMapper.mapToUserDto(user.get());
//    }

//    public List<UserDto> getAllByStatusAndLastName(String status, String lastName) {
//        List<UserDto> users = userRepository.getUserByStatusAndLastName(status, lastName).stream()
//                .map(u -> userMapper.mapToUserDto(u))
//                .collect(Collectors.toList());
//        if (users.isEmpty()) {
//            throw new NotFoundException(String.format("No user with status %s and last name %s found", status, lastName));
//        }
//        return users;
//    }

    public String deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("No user with username: %s was found", username));
        }
        userRepository.delete(user.get());

        this.auditRepository.save(new Audit("deleteUser", new Timestamp(System.currentTimeMillis()) ));
        return "User: " + username + " was deleted";
    }

    public UserDto updateStatus(Long id, String status) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("No user with id %s found", id));
        }
        switch (StatusType.valueOf(status)) {
            case FLAKE:
                user.get().setStatus(StatusType.FLAKE);
                break;
            case RELIABLE:
                user.get().setStatus(StatusType.RELIABLE);
                break;
            case BANNED:
                user.get().setStatus(StatusType.BANNED);
                break;
            default:
                throw new NotFoundException("Status not ok");
        }

        this.auditRepository.save(new Audit("getById", new Timestamp(System.currentTimeMillis()) ));
        return userMapper.mapToUserDto(userRepository.save(user.get()));

//        User user = userRepository.getReferenceById(id);
//        if (user == null) {
//            throw new NotFoundException(String.format("No user with id %s found", id));
//        }
//        switch (StatusType.valueOf(status)) {
//            case FLAKE:
//                user.setStatus(StatusType.FLAKE);
//                break;
//            case RELIABLE:
//                user.setStatus(StatusType.RELIABLE);
//                break;
//            case BANNED:
//                user.setStatus(StatusType.BANNED);
//                break;
//            default:
//                throw new NotFoundException("Status not ok");
//        }
//
//        return userMapper.mapToUserDto(userRepository.save(user));
//        return userMapper.mapToUserDto(userRepository.findById(id).get());
    }
}
