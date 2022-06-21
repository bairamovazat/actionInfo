package ru.itis.azat.ojs.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.itis.azat.ojs.forms.TokenForm;

@Component
public class TokenFormValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.getName().equals(TokenForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {
        TokenForm form = (TokenForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty.name", "Пустое имя");

    }
}
