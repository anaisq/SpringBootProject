package main.service;

import main.dto.UserDto;
import main.entity.RoleType;
import main.entity.StatusType;
import main.entity.User;
import main.exception.UserAlreadyOnDbException;
import main.exception.UserNotFoundException;
import main.mapper.UserMapper;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto addUser(UserDto userDto) {
        Optional<User> existingUsername = userRepository.findByUsername(userDto.getUsername());
        existingUsername.ifPresent(e -> {
            throw new UserAlreadyOnDbException("An user with same username " + userDto.getUsername() + " already exists");
        } );
        return userMapper.mapToUserDto(userRepository.save(userMapper.mapToUser(userDto)));
    }

    public List<UserDto> getUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> userMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + "attempted to be retrieved");
        }
        return Optional.of(userMapper.mapToUserDto(user.get()));
    }

    public List<UserDto> getUserByRoleAndStatus(RoleType role, StatusType status) {
        List<User> users = userRepository.findByRoleOrStatus(role, status);
        if(users.isEmpty()) {
            throw new UserNotFoundException(String.format("No user with role: %s was found", role));
        }
        return users.stream()
                .map(user -> userMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }


    public UserDto getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("No user with username: %s was found", username));
        }
        return userMapper.mapToUserDto(user.get());
    }

    public List<UserDto> getAllByStatusAndLastName(String status, String lastName) {
        List<UserDto> users = userRepository.getUserByStatusAndLastName(status, lastName).stream()
                .map(u -> userMapper.mapToUserDto(u))
                .collect(Collectors.toList());
        if (users.isEmpty()) {
            throw new UserNotFoundException(String.format("No user with status %s and last name %s found", status, lastName));
        }
        return users;
    }

    public String deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("No user with username: %s was found", username));
        }
        userRepository.delete(user.get());
        return "User: " + username + " was deleted";
    }

    public UserDto updateUsername(Long id, String username) {
        User user = userRepository.getReferenceById(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("No user with id %s found", id));
        }
        user.setUsername(username);
        return userMapper.mapToUserDto(userRepository.save(user));
    }
}
