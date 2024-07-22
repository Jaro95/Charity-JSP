package pl.coderslab.charity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 30)
    private String name;
    @NotBlank
    @Size(max = 30)
    private String lastName;
    @NotBlank
    private String password;
    private boolean enabled;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> role;
    private String token;
    private LocalDateTime createdAccount;
}
