package main.entity;

import javax.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user_books")
public class UserBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "id_book", nullable = false)
    private Long idBook;

    @Column(name = "borrowed_date")
    private final Timestamp borrowedDate = new Timestamp(System.currentTimeMillis());

    @Column(name = "delay")
    private boolean delay = false;
}
