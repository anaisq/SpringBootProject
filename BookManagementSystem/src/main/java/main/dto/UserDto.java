package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entity.RoleType;
import main.entity.StatusType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private RoleType role;
    private String username;
    private String firstName;
    private String lastName;
    private String cnp;
    private String address;
    private String email;
    private long phoneNumber;
    private StatusType status;
}
