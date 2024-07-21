package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    @Query("select r from Role r WHERE r.name != 'ROLE_SUPER_ADMIN'")
    Set<Role> onlyNotSuperAdmin();
}
