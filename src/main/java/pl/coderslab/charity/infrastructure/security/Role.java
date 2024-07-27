package pl.coderslab.charity.infrastructure.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import pl.coderslab.charity.domain.user.User;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String name;
    @ManyToMany(mappedBy = "role")
    private Set<User> user;
    @Override
    public String getAuthority() {
        return name;
    }
}
