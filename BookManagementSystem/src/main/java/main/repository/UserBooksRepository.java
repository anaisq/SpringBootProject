package main.repository;

import main.entity.Book;
import main.entity.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBooksRepository extends JpaRepository<UserBooks, Long> {

    /*
    @Query("SELECT u FROM UserBooks u WHERE u.idUser = :id")
    List<UserBooks> getByIdUser(Long id);
     */
    List<UserBooks> findByIdUser(Long id);

    Optional<UserBooks> findByIdUserAndIdBook(Long idUser, Long idBook);
}
