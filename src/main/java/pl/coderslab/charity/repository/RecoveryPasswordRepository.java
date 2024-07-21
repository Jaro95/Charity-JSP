package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.model.RecoveryPassword;

import java.util.List;
import java.util.Optional;

public interface RecoveryPasswordRepository extends JpaRepository<RecoveryPassword, Long> {
    Optional<RecoveryPassword> findByEmail(String email);
    Optional<RecoveryPassword> findByTokenRecoveryPassword(String token);
}
