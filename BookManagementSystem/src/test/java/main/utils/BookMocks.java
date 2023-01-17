package main.utils;

import main.dto.BookDto;
import main.entity.*;

public class BookMocks {
    public static Book mockBook() {
        return Book.builder()
                .id(1L)
                .title("Ion")
                .author("Liviu Rebreanu")
                .numberOfCopies(3)
                .numberOfAvailableCopies(3)
                .bookDetails(BookDetails.builder()
                        .part(1)
                        .categoryTpe(CategoryType.FICTION)
                        .isbn("ISBN-10- 0-596-52068-3")
                        .price(14.59F)
                        .build())
                .build();
    }

    public static BookDto mockBookDto() {
        return BookDto.builder()
                .title("Ion")
                .author("Liviu Rebreanu")
                .numberOfCopies(3)
                .numberOfAvailableCopies(3)
                .part(1)
                .categoryTpe(CategoryType.FICTION)
                .isbn("ISBN-10- 0-596-52068-3")
                .price(14.59F)
                .build();
    }
}
