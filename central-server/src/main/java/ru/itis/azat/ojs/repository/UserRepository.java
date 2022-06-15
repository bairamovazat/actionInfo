package ru.itis.azat.ojs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.azat.ojs.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUuid(UUID uuid);

    Optional<User> findOneByLogin(String login);

    Optional<User> findById(Long userId);

    Optional<User> findByUuid(UUID uuid);
}
