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

import java.util.Optional;

@Service
public class UserBooksServiceTestImpl implements UserBooksService{

    @Autowired
    private UserBooksRepository userBooksRepository;


    public UserBooks add(UserBooks userBooks) {
        return userBooksRepository.save(userBooks);
    }

}
