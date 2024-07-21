package pl.coderslab.charity.service;

import pl.coderslab.charity.dto.RegistrationDTO;
import pl.coderslab.charity.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    void saveUser(RegistrationDTO user);
    void updateUser(User user, String password);
    void updateUser(User user);
    void sendRecoveryPasswordEmail(String email);
    void resetPassword(String email, String password);
    void resetPasswordForAdmin(Long id, String password);
}
