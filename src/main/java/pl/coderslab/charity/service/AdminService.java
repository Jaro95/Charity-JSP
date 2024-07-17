package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.repository.RoleRepository;
import pl.coderslab.charity.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createRole() {
        if (roleRepository.findAll().isEmpty()) {
            Role superAdminRole = Role.builder().name("ROLE_SUPER_ADMIN").build();
            Role adminRole = Role.builder().name("ROLE_ADMIN").build();
            Role userRole = Role.builder().name("ROLE_USER").build();

            roleRepository.saveAll(List.of(superAdminRole, adminRole, userRole));
        }
    }

    public void createBasicCategory() {
        if (categoryRepository.findAll().isEmpty()) {
            List<Category> basic = List.of(
                    Category.builder().name("ubrania, które nadają się do ponownego użycia").build(),
                    Category.builder().name("ubrania, do wyrzucenia").build(),
                    Category.builder().name("zabawki").build(),
                    Category.builder().name("książki").build(),
                    Category.builder().name("inne").build()
            );
            categoryRepository.saveAll(basic);
        }
    }

    public void createBasicInstitution() {
        if (institutionRepository.findAll().isEmpty()) {
            List<Institution> basic = List.of(
                    new Institution("Dbam o Zdrowie", "Pomoc dzieciom z ubogich rodzin."),
                    new Institution("A kogo", "Pomoc wybudzaniu dzieci ze śpiączki"),
                    new Institution("Dla dzieci", "Pomoc osobom znajdującym się w trudnej sytuacji życiowej."),
                    new Institution("Bez domu", "Pomoc dla osób nie posiadających miejsca zamieszkania")
            );
            institutionRepository.saveAll(basic);
        }
    }

    public void createBasicAdmin() {
        if (userRepository.findAll().isEmpty()) {
            Role roleSuperAdmin = roleRepository.findByName("ROLE_SUPER_ADMIN");
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
            Role roleUser = roleRepository.findByName("ROLE_USER");
            List<User> userList = List.of(
                    User.builder()
                            .email("superAdmin@admin")
                            .name("Jarek")
                            .lastName("Marciniak")
                            .roles(new HashSet<>(Arrays.asList(roleSuperAdmin,roleAdmin,roleUser)))
                            .password(passwordEncoder.encode("admin"))
                            .createdAccount(LocalDateTime.now())
                            .build(),
                    User.builder()
                            .email("admin@admin")
                            .name("Krzysztof")
                            .lastName("Wysocki")
                            .roles(new HashSet<>(Arrays.asList(roleAdmin,roleUser)))
                            .password(passwordEncoder.encode("admin"))
                            .createdAccount(LocalDateTime.now())
                            .build());
            userRepository.saveAll(userList);

        }
    }
}
