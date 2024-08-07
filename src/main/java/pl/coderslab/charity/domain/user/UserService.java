package pl.coderslab.charity.domain.user;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    void saveUser(RegistrationRequest user);
    void updateUser(User user, String password);
    void updateUser(User user);
    void sendRecoveryPasswordEmail(String email);
    void resetPassword(String email, String password);
    void resetPasswordForAdmin(Long id, String password);
}
