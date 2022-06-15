package ru.itis.azat.ojs.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import ru.itis.azat.ojs.model.User;

public interface AuthenticationService {
    User getCurrentUser();

    User getUserByAuthentication(Authentication authentication);

    void putUserToModelIfExists(Authentication authentication, ModelMap model);
}
