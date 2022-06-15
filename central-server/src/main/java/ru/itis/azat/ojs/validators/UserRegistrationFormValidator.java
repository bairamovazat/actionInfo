package ru.itis.azat.ojs.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.itis.azat.ojs.forms.UserRegistrationForm;
import ru.itis.azat.ojs.model.User;
import ru.itis.azat.ojs.repository.UserRepository;

import java.util.Optional;

@Component
public class UserRegistrationFormValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.getName().equals(UserRegistrationForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationForm form = (UserRegistrationForm)target;

        Optional<User> existedUser = userRepository.findOneByLogin(form.getLogin());

        if (existedUser.isPresent()) {
            errors.reject("bad.login", "Логин занят");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty.name", "Пустое имя");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "empty.login", "Пустой логин");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty.password", "Пустой пароль");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty.email", "Пустоя почта");

    }
}
