package ru.itis.azat.ojs.service;

import ru.itis.azat.ojs.forms.UserRegistrationForm;

public interface RegistrationService {
    void register(UserRegistrationForm userForm);
}
