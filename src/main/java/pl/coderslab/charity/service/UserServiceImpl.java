package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.dto.RegistrationDTO;
import pl.coderslab.charity.model.RecoveryPassword;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.RecoveryPasswordRepository;
import pl.coderslab.charity.repository.RoleRepository;
import pl.coderslab.charity.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

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
    public void saveUser(RegistrationDTO user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        userRepository.save(User.builder()
                .email(user.getEmail())
                .name(user.getFirstName())
                .lastName(user.getLastName())
                .roles(new HashSet<>(Arrays.asList(userRole)))
                .password(passwordEncoder.encode(user.getPassword()))
                .createdAccount(LocalDateTime.now())
                .enabled(false)
                .token(UUID.randomUUID().toString())
                .build());
        emailService.sendVerificationEmail(user.getEmail(), userRepository.findByEmail(user.getEmail()).get().getToken());
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

    public void deleteOccurrenceEmailInListReset(String email) {
        Optional<RecoveryPassword> validOccurrenceEmail = recoveryPasswordRepository.findByEmail(email);
        validOccurrenceEmail.ifPresent( e ->
                recoveryPasswordRepository.delete(validOccurrenceEmail.get())
        );
    }
}
