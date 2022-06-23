package ru.itis.azat.ojs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.azat.ojs.model.ActionInfo;
import ru.itis.azat.ojs.model.ActionInfoUser;

import java.util.Optional;

public interface ActionInfoUserRepository extends JpaRepository<ActionInfoUser, Long> {

    Optional<ActionInfoUser> findFirstByOjsIdAndUsername(Long id, String username);

}
