package pl.coderslab.charity.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.infrastructure.security.Role;
import pl.coderslab.charity.infrastructure.security.RoleRepository;

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
                .role(new HashSet<>(Collections.singletonList(userRole)))
                .password(passwordEncoder.encode(user.getPassword()))
                .createdAccount(LocalDateTime.now())
                .enabled(false)
                .token(UUID.randomUUID().toString())
                .build());
        emailService.sendVerificationEmail(user.getEmail(), userRepository.findByEmail(user.getEmail()).orElseThrow().getToken());
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
        emailService.sendResetPassword(email, recoveryPassword.orElseThrow().getTokenRecoveryPassword());
    }

    @Override
    public void resetPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(e -> {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            deleteOccurrenceEmailInListReset(email);
            passwordChangeSuccess(user.get());
        });
    }

    @Override
    public void resetPasswordForAdmin(Long id, String password) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(e -> {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            passwordChangeSuccess(user.get());
        });
    }

    public void passwordChangeSuccess(User user) {
        log.info("User {} changed password", user.getEmail());
    }

    public void deleteOccurrenceEmailInListReset(String email) {
        Optional<RecoveryPassword> validOccurrenceEmail = recoveryPasswordRepository.findByEmail(email);
        validOccurrenceEmail.ifPresent( e ->
                recoveryPasswordRepository.delete(validOccurrenceEmail.get())
        );
    }
}
