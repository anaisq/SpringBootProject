package main.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.Min;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "number_of_copies", nullable = false)
    @Min(0)
    private Integer numberOfCopies;

    @Column(name = "number_of_available_copies", nullable = false)
    private Integer numberOfAvailableCopies;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_book_details")
    private BookDetails bookDetails;

    @ManyToMany(mappedBy = "books")
    //@JsonBackReference
    private List<User> users;
}
