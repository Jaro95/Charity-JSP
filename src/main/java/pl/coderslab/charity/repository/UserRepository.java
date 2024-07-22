package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);

    @Query("SELECT u FROM User u JOIN u.role r where r.name = 'ROLE_ADMIN'")
    List<User> allAdmins();

    @Query("SELECT u FROM User u JOIN u.role r GROUP BY u HAVING COUNT(r) = 1")
    List<User> allUsers();
}
