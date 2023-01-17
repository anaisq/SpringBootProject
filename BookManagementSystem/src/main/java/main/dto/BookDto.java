package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entity.BookDetails;
import main.entity.CategoryType;
import main.entity.User;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import java.util.List;

import static main.constants.Constants.ISBN_REGEX;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private Integer numberOfCopies;
    private Integer numberOfAvailableCopies;
    private int part;
    private CategoryType categoryTpe;
    private String isbn;
    private float price;
    private List<User> users;
}
