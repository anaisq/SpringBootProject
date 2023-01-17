package main.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_details")
    @JsonManagedReference
    private UserDetails userDetails;

    @ManyToMany()
    @JoinTable(name = "user_books",
    joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "id_book", referencedColumnName = "id"))
    @JsonManagedReference
    private List<Book> books;

}
