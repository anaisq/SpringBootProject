package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entity.*;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static main.constants.Constants.EMAIL_REGEX;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private RoleType role;
    private String username;
    private StatusType status;
    private String firstName;
    private String lastName;
    private String cnp;
    private String address;
    private String email;
    private String phoneNumber;
    private List<Book> books;
}
