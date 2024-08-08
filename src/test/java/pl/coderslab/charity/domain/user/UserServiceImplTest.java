package pl.coderslab.charity.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.charity.domain.dataFactory.TestDataFactory;
import pl.coderslab.charity.infrastructure.security.RoleRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private RecoveryPasswordRepository recoveryPasswordRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = TestDataFactory.createUser();
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> findUser = userService.findByEmail(user.getEmail());

        assertTrue(findUser.isPresent());
        assertEquals(user, findUser.get());
    }

    @Test
    void findByToken() {
        when(userRepository.findByToken(user.getToken())).thenReturn(Optional.of(user));

        Optional<User> findUser = userService.findByToken(user.getToken());

        assertTrue(findUser.isPresent());
        assertEquals(user, findUser.get());
    }

    @Test
    void testSaveUser() {
        RegistrationRequest addUser = TestDataFactory.addUser();
        when(roleRepository.findByName("ROLE_USER")).thenReturn(TestDataFactory.createRole());
        when(passwordEncoder.encode(addUser.getPassword())).thenReturn(addUser.getPassword());
        when(userRepository.findByEmail(addUser.getEmail())).thenReturn(Optional.of(user));

        userService.saveUser(addUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updatePasswordUser() {
        when(passwordEncoder.matches(user.getPassword(), "password")).thenReturn(false);

        userService.updateUser(user, "password");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        userService.updateUser(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSendRecoveryPasswordEmail() {
        RecoveryPassword recoveryPassword = TestDataFactory.createRecoveryPassword();
        when(recoveryPasswordRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(recoveryPassword));

        userService.sendRecoveryPasswordEmail(user.getEmail());

        verify(recoveryPasswordRepository, times(1)).save(any(RecoveryPassword.class));
    }

    @Test
    void resetPassword() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        userService.resetPassword(user.getEmail(), TestDataFactory.addUser().getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void resetPasswordForAdmin() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.resetPasswordForAdmin(1L, "new password");

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void deleteOccurrenceEmailInListReset() {
        String email = "Token";
        when(recoveryPasswordRepository.findByEmail(email)).thenReturn(Optional.of(getRecoveryPassword()));

        userService.deleteOccurrenceEmailInListReset(email);

        verify(recoveryPasswordRepository, times(1)).delete(any(RecoveryPassword.class));
    }

    public RecoveryPassword getRecoveryPassword() {
        return new RecoveryPassword(1L, user.getEmail(), "Token", LocalDateTime.now());
    }
}