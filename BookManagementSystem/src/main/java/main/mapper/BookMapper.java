package main.mapper;

import main.dto.BookDto;
import main.dto.UserDto;
import main.entity.Book;
import main.entity.BookDetails;
import main.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book mapToBook(BookDto bookDto){
        return Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .numberOfCopies(bookDto.getNumberOfCopies())
                .numberOfAvailableCopies(bookDto.getNumberOfAvailableCopies())
                .bookDetails(BookDetails.builder()
                        .part(bookDto.getPart())
                        .categoryTpe(bookDto.getCategoryTpe())
                        .isbn(bookDto.getIsbn())
                        .price(bookDto.getPrice())
                        .build())
                .users(bookDto.getUsers())
                .build();
    }

    public BookDto mapToBookDto(Book book){
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .numberOfCopies(book.getNumberOfCopies())
                .numberOfAvailableCopies(book.getNumberOfAvailableCopies())
                .part(book.getBookDetails().getPart())
                .categoryTpe(book.getBookDetails().getCategoryTpe())
                .isbn(book.getBookDetails().getIsbn())
                .price(book.getBookDetails().getPrice())
                .users(book.getUsers())
                .build();
    }
}
