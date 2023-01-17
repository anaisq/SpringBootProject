package main.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;

import static main.constants.Constants.EMAIL_REGEX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_details")
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "cnp", nullable = false)
    private String cnp;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    @Pattern(regexp = EMAIL_REGEX)
    private String email;

    @Column(name = "phone_number", nullable = false)
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @OneToOne(mappedBy = "userDetails")
    // @JsonBackReference
    private User user;
}
