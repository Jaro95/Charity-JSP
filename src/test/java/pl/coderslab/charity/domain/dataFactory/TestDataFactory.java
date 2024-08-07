package pl.coderslab.charity.domain.dataFactory;

import pl.coderslab.charity.domain.user.RecoveryPassword;
import pl.coderslab.charity.domain.user.RegistrationRequest;
import pl.coderslab.charity.domain.user.User;
import pl.coderslab.charity.infrastructure.security.Role;

import java.time.LocalDateTime;
import java.util.Set;

public class TestDataFactory {

    public static Set<Role> createRoleList() {
        return Set.of(createRole());
    }

    public static Role createRole() {
        return Role.builder().name("USER").build();
    }

    public static User createUser() {
        return User.builder()
                .id(1)
                .email("u@u")
                .name("userName")
                .lastName("userLastName")
                .password("password")
                .enabled(true)
                .role(createRoleList())
                .token("token")
                .createdAccount(LocalDateTime.of(2024, 8, 1, 14, 0, 0))
                .build();
    }

    public static RegistrationRequest addUser() {
        return RegistrationRequest.builder()
                .email("new@u")
                .firstName("new userName")
                .lastName("new userLastName")
                .password("new password")
                .build();
    }

    public static RecoveryPassword createRecoveryPassword() {
        return RecoveryPassword.builder()
                .id(1L)
                .email("u@u")
                .tokenRecoveryPassword("Recovery Token")
                .localDateTime(LocalDateTime.of(2024, 8, 1, 14, 0, 0))
                .build();
    }
}
