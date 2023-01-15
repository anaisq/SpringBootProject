package main.repository;

import main.entity.RoleType;
import main.entity.StatusType;
import main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //1. query from method name

    Optional<User> findByUsername(String username);

    List<User> findByRoleOrStatus(RoleType role, StatusType status);


    //2. JPQL - queries on entities
    @Query("SELECT u FROM User u WHERE u.status = :status AND u.lastName = :lastName")
    List<User> getUserByStatusAndLastName(String status, String lastName);

    //3. native query
//    @Query(nativeQuery = true,
//            value = "SELECT * FROM users us WHERE us.role = :role OR us.status =: status")
//    List<User> getByRoleOrStatus(RoleType role, StatusType status);

}
