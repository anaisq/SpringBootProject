package main.service;

import main.entity.Book;
import main.entity.StatusType;
import main.entity.User;
import main.entity.UserBooks;
import main.exception.NotAllowed;
import main.exception.NotEnoughResources;
import main.exception.NotFoundException;
import main.repository.BookRepository;
import main.repository.UserBooksRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBooksServiceImpl implements UserBooksService {

    @Autowired
    private UserBooksRepository userBooksRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public UserBooks add(UserBooks userBooks) {
        Optional<User> user = userRepository.findById(userBooks.getIdUser());
        Optional<Book> book = bookRepository.findById(userBooks.getIdBook());
        if(user.isEmpty() || book.isEmpty()) {
            throw new NotFoundException("User with id " + userBooks.getIdUser() + " or book id: " + userBooks.getIdBook() + " does not exist");
        }
        if(user.get().getStatus() != StatusType.RELIABLE){
            throw new NotAllowed("User is either BANNED or a FLAKE.");
        }
        if(book.get().getNumberOfAvailableCopies() == 0){
            throw new NotEnoughResources(String.format("Not enough copies available for lending"));
        }
        return userBooksRepository.save(userBooks);
    }

    public List<UserBooks> getById(Long id) {
//        Optional<User> user = userRepository.findById(id);
//        if(user.isEmpty()) {
//            throw new NotFoundException("User with id " + id + " does not exist");
//        }
        List<UserBooks> userBooks = userBooksRepository.findByIdUser(id);
        if(userBooks.isEmpty()) {
            throw new NotFoundException("User with id " + id + " does not have borrowings");
        }
        return userBooks;
    }

    public UserBooks updateDelay(Long idU, Long idB) {
        Optional<UserBooks> userBook = userBooksRepository.findByIdUserAndIdBook(idU, idB);

        Optional<UserBooks> userBook1 =  userBooksRepository.findById(userBook.get().getId());

        if(userBook1.get().isDelay()){
            userBook1.get().setDelay(false);
        }
        else{
            userBook1.get().setDelay(false);
        }
        userBooksRepository.save(userBook1.get());
        return userBook.get();

    }
}
