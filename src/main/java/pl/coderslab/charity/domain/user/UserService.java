package pl.coderslab.charity.domain.user;

import org.springframework.validation.BindingResult;

import java.util.List;
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
    List<User> getAllUsers();
    List<User> getOnlyUsers();
    List<User> getOnlyAdmins();
    List<User> getUsersWithRole(String role);
    String registrationUser(RegistrationRequest registrationRequest, BindingResult bindingResult);
    String activateUser(String token);
    String resetPasswordCheckEmail(String email);
    String resetPasswordCheckToken(String token);
    String resetPassword(ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult);
}
