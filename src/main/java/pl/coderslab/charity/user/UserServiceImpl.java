package pl.coderslab.charity.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.coderslab.charity.security.Role;
import pl.coderslab.charity.security.RoleRepository;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final RecoveryPasswordRepository recoveryPasswordRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public void saveUser(RegistrationRequest user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        userRepository.save(User.builder()
                .email(user.getEmail())
                .name(user.getFirstName())
                .lastName(user.getLastName())
                .role(new HashSet<>(Arrays.asList(userRole)))
                .password(passwordEncoder.encode(user.getPassword()))
                .createdAccount(LocalDateTime.now())
                .enabled(false)
                .token(UUID.randomUUID().toString())
                .build());
        //emailService.sendVerificationEmail(user.getEmail(), userRepository.findByEmail(user.getEmail()).get().getToken());
    }

    @Override
    public void updateUser(User user, String password) {
        if (!passwordEncoder.matches(user.getPassword(), password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void sendRecoveryPasswordEmail(String email) {
        deleteOccurrenceEmailInListReset(email);
        recoveryPasswordRepository.save(RecoveryPassword.builder()
                .email(email)
                .tokenRecoveryPassword(UUID.randomUUID().toString())
                .localDateTime(LocalDateTime.now())
                .build());
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByEmail(email);
        emailService.sendResetPassword(email, recoveryPassword.get().getTokenRecoveryPassword());
    }

    @Override
    public void resetPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(e -> {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            deleteOccurrenceEmailInListReset(email);
            log.info("User {} changed password", user.get().getEmail());
        });
    }

    @Override
    public void resetPasswordForAdmin(Long id, String password) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(e -> {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            log.info("User {} changed password", user.get().getEmail());
        });
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            for (Role role : user.getRole()) {
                role.getUser().clear();
            }
        }
        return users;
    }

    @Override
    public List<User> getOnlyUsers() {
        List<User> users = userRepository.onlyUsers();
        for (User user : users) {
            for (Role role : user.getRole()) {
                role.getUser().clear();
            }
        }
        return users;
    }

    @Override
    public List<User> getOnlyAdmins() {
        List<User> users = userRepository.onlyAdmins();
        for (User user : users) {
            for (Role role : user.getRole()) {
                role.getUser().clear();
            }
        }
        return users;
    }

    @Override
    public String registrationUser(RegistrationRequest registrationRequest, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().toString();
        }
        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatPassword())) {
            return "Passwords are not the same";
        }
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            return "Email is already taken";
        }
        saveUser(registrationRequest);
        log.info("Added new user:\nEmail:{}\nName:{}\nLast name:{}", registrationRequest.getEmail(),
                registrationRequest.getFirstName(),registrationRequest.getLastName());
        return "Registration successful";
    }

    @Override
    public String activateUser(String token) {
        Optional<User> user = findByToken(token);
        if (user.isEmpty()) {
            return "Token invalid or expired";
        }
        user.get().setEnabled(true);
        user.get().setToken("verified");
        updateUser(user.get());
        return "The account has been activated";
    }

    @Override
    public String resetPasswordCheckEmail(String email) {
        Optional<User> user = findByEmail(email);
        if (user.isEmpty()) {
            return "Wrong Email";
        }
        sendRecoveryPasswordEmail(email);
        return "A password reset link has been sent to your email address";
    }

    @Override
    public String resetPasswordCheckToken(String token) {
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByTokenRecoveryPassword(token);
        if (recoveryPassword.isEmpty()) {
            return "Token is invalid";
        }
        return "Token is valid";
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest, BindingResult result) {
        Optional<RecoveryPassword> recoveryPassword = recoveryPasswordRepository.findByTokenRecoveryPassword(resetPasswordRequest.token());
        if (recoveryPassword.isEmpty()) {
            return "Token is invalid";
        }
        if (result.hasErrors()) {
            return result.getAllErrors().toString();
        }
        if (!resetPasswordRequest.password().equals(resetPasswordRequest.repeatPassword())) {
            return "Passwords are not the same";
        }
        resetPassword(recoveryPassword.get().getEmail(), resetPasswordRequest.password());
        return "The password has been changed";
    }

    public void deleteOccurrenceEmailInListReset(String email) {
        Optional<RecoveryPassword> validOccurrenceEmail = recoveryPasswordRepository.findByEmail(email);
        validOccurrenceEmail.ifPresent( e ->
                recoveryPasswordRepository.delete(validOccurrenceEmail.get())
        );
    }
}
