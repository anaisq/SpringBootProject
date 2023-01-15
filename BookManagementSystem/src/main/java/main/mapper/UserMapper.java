package main.mapper;

import main.dto.UserDto;
import main.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto){
        return User.builder()
                .role(userDto.getRole())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .cnp(userDto.getCnp())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .status(userDto.getStatus())
                .build();
    }

    public UserDto mapToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .cnp(user.getCnp())
                .address(user.getAddress())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .build();
    }
}
