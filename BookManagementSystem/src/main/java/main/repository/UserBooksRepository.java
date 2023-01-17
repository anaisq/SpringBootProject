package main.repository;

import main.entity.Book;
import main.entity.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBooksRepository extends JpaRepository<UserBooks, Long> {

    List<UserBooks> findByIdUser(Long id);

    Optional<UserBooks> findByIdUserAndIdBook(Long idUser, Long idBook);
}
