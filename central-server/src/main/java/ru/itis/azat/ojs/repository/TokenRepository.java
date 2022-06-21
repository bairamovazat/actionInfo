package ru.itis.azat.ojs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.azat.ojs.model.Token;
import ru.itis.azat.ojs.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String value);

    List<Token> findAllByUser(User user);

}
