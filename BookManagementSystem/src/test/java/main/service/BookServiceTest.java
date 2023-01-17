package main.service;

import main.dto.BookDto;
import main.dto.UserDto;
import main.entity.BookDetails;
import main.entity.CategoryType;
import main.entity.StatusType;
import main.entity.Book;
import main.exception.AlreadyOnDbException;
import main.exception.NotEnoughResources;
import main.exception.NotFoundException;
import main.mapper.BookMapper;
import main.repository.BookDetailsRepository;
import main.repository.BookRepository;
import main.utils.BookMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookDetailsRepository bookDetailsRepository;

    @Mock
    private BookMapper bookMapper;

    private Book book;
    private BookDto bookDto;

    @Test
    public void whenBookDoesntExist_create_saveTheBook(){
        // Arrange
        book = BookMocks.mockBook();
        bookDto = BookMocks.mockBookDto();
        BookDetails bookDetails = BookDetails.builder()
                .part(1)
                .categoryTpe(CategoryType.FICTION)
                .isbn("ISBN-10- 0-596-52068-3")
                .price(14.59F)
                .build();

        // Act
        when(bookRepository.findByTitle(bookDto.getTitle())).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);
        when(bookDetailsRepository.findById(1L)).thenReturn(Optional.of(bookDetails));
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);
        when(bookMapper.mapToBook(bookDto)).thenReturn(book);

        BookDto result = bookService.addBook(bookDto, 1L);

        // Assert

        assertTrue(result.getTitle().equals(bookDto.getTitle()));
        assertNotNull(result);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    public void whenBookExist_throwException(){
        // Arrange
        book = BookMocks.mockBook();
        bookDto = BookMocks.mockBookDto();

        // Act
        when(bookRepository.findByTitle(bookDto.getTitle())).thenReturn(Optional.of(book));

        AlreadyOnDbException alreadyOnDbException =
                assertThrows(AlreadyOnDbException.class , () -> bookService.addBook(bookDto,1L));

        // Assert
        assertEquals("A book with same title " + bookDto.getTitle() + " already exists", alreadyOnDbException.getMessage());
    }

    @Test
    public void whenBookExist_updateDecrIncr(){
        // Arrange
        book = BookMocks.mockBook();
        // bookDto = BooksMocks.mockBookDto();
        Long id = 1L;

        // Act
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        NotEnoughResources notEnoughResources =
                assertThrows(NotEnoughResources.class , () -> bookService.incrementNumberOfAvailableCopiesByOne(id));

        // Assert
        assertEquals(String.format("Number of available copies cannot be grater then number of copies"), notEnoughResources.getMessage());

    }

    @Test
    public void whenBookExist_deleteBook(){
        // Arrange
        book = BookMocks.mockBook();
        // bookDto = BooksMocks.mockBookDto();
        Long id = 1L;

        // Act
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        String result = bookService.deleteBook(id);
        // Assert

        assertEquals(result, "Book with id: " + id + " was deleted");
    }

    @Test
    public void whenBookDoesntExist_deleteBook(){
        // Arrange
        book = BookMocks.mockBook();
        // bookDto = BooksMocks.mockBookDto();
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        // Act
        NotFoundException notFoundException =
                assertThrows(NotFoundException.class , () -> bookService.deleteBook(id));

        // Assert
        assertEquals(String.format("No book with id: %s was found", id), notFoundException.getMessage());
    }

    @Test
    public void whenBooksExist_GetAll(){
        // Arrange
        book = BookMocks.mockBook();
        bookDto = BookMocks.mockBookDto();

        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);
        // Act
        List<BookDto> bookList = bookService.getBooks();

        List<BookDto> bookDtoList = List.of(bookDto);

        // Assert
        assertEquals(bookDtoList, bookList);
    }

    @Test
    public void whenBooksExist_GetById(){
        // Arrange
        book = BookMocks.mockBook();
        bookDto = BookMocks.mockBookDto();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);
        // Act
        Optional<BookDto> bookResult = bookService.getById(1L);

        // Assert
        assertEquals(Optional.of(bookDto), bookResult);
    }

    @Test
    public void whenBooksExist_GetByTitle(){
        // Arrange
        book = BookMocks.mockBook();
        bookDto = BookMocks.mockBookDto();

        when(bookRepository.findByTitle("Ion")).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);
        // Act
        Optional<BookDto> bookResult = bookService.getBookByTitle("Ion");

        // Assert
        assertEquals(Optional.of(bookDto), bookResult);
    }
}
