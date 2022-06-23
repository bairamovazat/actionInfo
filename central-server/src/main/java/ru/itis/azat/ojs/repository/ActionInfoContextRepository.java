package ru.itis.azat.ojs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.azat.ojs.model.ActionInfo;
import ru.itis.azat.ojs.model.ActionInfoContext;
import ru.itis.azat.ojs.model.ActionInfoUser;

import java.util.Optional;

public interface ActionInfoContextRepository extends JpaRepository<ActionInfoContext, Long> {

    Optional<ActionInfoContext> findFirstByOjsIdAndUrlPath(Long id, String urlPath);

}
