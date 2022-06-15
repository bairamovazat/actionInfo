package ru.itis.azat.ojs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.azat.ojs.security.details.Role;

public interface RoleRepository extends JpaRepository<ru.itis.azat.ojs.model.Role, Long> {
    ru.itis.azat.ojs.model.Role findFirstByRole(Role role);
}
