package main.mapper;

import main.dto.UserDto;
import main.entity.User;
import main.entity.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto){
        return User.builder()
                .role(userDto.getRole())
                .username(userDto.getUsername())
                .status(userDto.getStatus())
                .userDetails(UserDetails.builder()
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .cnp(userDto.getCnp())
                        .address(userDto.getAddress())
                        .email(userDto.getEmail())
                        .phoneNumber(userDto.getPhoneNumber())
                        .build())
                .books(userDto.getBooks())
                .build();
    }

    public UserDto mapToUserDto(User user){
//        return UserDto.builder()
//                .id(user.getId())
//                .role(user.getRole())
//                .username(user.getUsername())
//                .status(user.getStatus())
//                .userDetails(user.getUserDetails())
//                .books(user.getBooks())
//                .build();
//
    return UserDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .username(user.getUsername())
                .status(user.getStatus())
                .firstName(user.getUserDetails().getFirstName())
                .lastName(user.getUserDetails().getLastName())
                .cnp(user.getUserDetails().getCnp())
                .address(user.getUserDetails().getAddress())
                .email(user.getUserDetails().getEmail())
                .phoneNumber(user.getUserDetails().getPhoneNumber())
            .books(user.getBooks())
                .build();

    }
}
