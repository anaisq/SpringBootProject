package main.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

import lombok.*;
import org.hibernate.validator.constraints.ISBN;

import java.util.List;

import static main.constants.Constants.ISBN_REGEX;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_details")
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book_details")
    private Long id;

    @Column(name = "part")
    private int part;

    @Column(name = "category", nullable = false)
    private CategoryType categoryTpe;

    @Column(name = "isbn")
    @Pattern(regexp = ISBN_REGEX)
    private String isbn;

    @Column(name = "price")
    private float price;

    @OneToOne(mappedBy = "bookDetails")
    private Book book;
}
