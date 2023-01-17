package main.utils;

import main.dto.UserDto;
import main.entity.RoleType;
import main.entity.StatusType;
import main.entity.User;
import main.entity.UserDetails;

public class UsersMocks {
    public static User mockUser() {
        return User.builder()
                .id(1L)
                .role(RoleType.ADMIN)
                .username("admin")
                .status(StatusType.RELIABLE)
                .userDetails(UserDetails.builder()
                        .firstName("Ana")
                        .lastName("Florea")
                        .cnp("2990613")
                        .address("sdfds")
                        .email("dds@dd.com")
                        .phoneNumber("1234567890")
                        .build())
                .build();
    }

    public static UserDto mockUserDto() {
        return UserDto.builder()
                .role(RoleType.ADMIN)
                .username("admin")
                .status(StatusType.RELIABLE)
                .firstName("Ana")
                .lastName("Florea")
                .cnp("2990613")
                .address("sdfds")
                .email("dds@dd.com")
                .phoneNumber("1234567890")
                .build();
    }
}
