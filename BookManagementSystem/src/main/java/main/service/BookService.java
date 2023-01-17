package main.service;

import main.dto.BookDto;
import main.entity.RoleType;
import main.entity.StatusType;
import main.entity.Book;
import main.exception.AlreadyOnDbException;
import main.exception.NotEnoughResources;
import main.exception.NotFoundException;
import main.mapper.BookMapper;
import main.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public BookDto addBook(BookDto bookDto) {
        Optional<Book> existingBookname = bookRepository.findByTitle(bookDto.getTitle());
        existingBookname.ifPresent(e -> {
            throw new AlreadyOnDbException("A book with same title " + bookDto.getTitle() + " already exists");
        } );
        return bookMapper.mapToBookDto(bookRepository.save(bookMapper.mapToBook(bookDto)));
    }

    public List<BookDto> getBooks(){
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> bookMapper.mapToBookDto(book))
                .collect(Collectors.toList());
    }

    public Optional<BookDto> getById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()) {
            throw new NotFoundException("Book with id: " + id + " not found.");
        }
        return Optional.of(bookMapper.mapToBookDto(book.get()));
    }

//    public Optional<BookDto> getByAuthor(String author) {
//        Optional<Book> book = bookRepository.findByAuthor(author);
//        if(book.isEmpty()) {
//            throw new NotFoundException("Author with name " + author + " not found.");
//        }
//        return Optional.of(bookMapper.mapToBookDto(book.get()));
//    }

//    public List<BookDto> getBookByRoleAndStatus(RoleType role, StatusType status) {
//        List<Book> books = bookRepository.findByRoleOrStatus(role, status);
//        if(books.isEmpty()) {
//            throw new BookNotFoundException(String.format("No book with role: %s was found", role));
//        }
//        return books.stream()
//                .map(book -> bookMapper.mapToBookDto(book))
//                .collect(Collectors.toList());
//    }


    public Optional<BookDto> getBookByTitle(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isEmpty()) {
            throw new NotFoundException(String.format("No book with bookname: %s was found", title));
        }
        return Optional.of(bookMapper.mapToBookDto(book.get()));
    }

//    public List<BookDto> getAllByStatusAndLastName(String status, String lastName) {
//        List<BookDto> books = bookRepository.getBookByStatusAndLastName(status, lastName).stream()
//                .map(u -> bookMapper.mapToBookDto(u))
//                .collect(Collectors.toList());
//        if (books.isEmpty()) {
//            throw new NotFoundException(String.format("No book with status %s and last name %s found", status, lastName));
//        }
//        return books;
//    }

    public String deleteBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new NotFoundException(String.format("No book with id: %s was found", id));
        }
        bookRepository.delete(book.get());
        return "Book with id: " + id + " was deleted";
    }

    public BookDto decrementNumberOfAvailableCopiesByOne(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new NotFoundException(String.format("No book with id %s found", id));
        }
        if (book.get().getNumberOfAvailableCopies() == 0){
            throw new NotEnoughResources(String.format("Number of available copies cannot be less then 0"));
        }
        book.get().setNumberOfAvailableCopies(book.get().getNumberOfAvailableCopies()-1);
        return bookMapper.mapToBookDto(bookRepository.save(book.get()));
//        Book book = bookRepository.getReferenceById(id);
//        if (book == null) {
//            throw new NotFoundException(String.format("No book with id %s found", id));
//        }
//        if (book.getNumberOfAvailableCopies() == 0){
//            throw new NotEnoughResources(String.format("Not enough copies available for lending"));
//        }
//        book.setNumberOfAvailableCopies(book.getNumberOfAvailableCopies()-1);
//        return bookMapper.mapToBookDto(bookRepository.save(book));
//         return bookMapper.mapToBookDto(bookRepository.findById(id).get());
    }

    public BookDto incrementNumberOfAvailableCopiesByOne(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new NotFoundException(String.format("No book with id %s found", id));
        }
        if (book.get().getNumberOfAvailableCopies() == book.get().getNumberOfCopies()){
            throw new NotEnoughResources(String.format("Number of available copies cannot be grater then number of copies"));
        }
        book.get().setNumberOfAvailableCopies(book.get().getNumberOfAvailableCopies()+1);
        return bookMapper.mapToBookDto(bookRepository.save(book.get()));
//        return bookMapper.mapToBookDto(bookRepository.findById(id).get());
    }


}
