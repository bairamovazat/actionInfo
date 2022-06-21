package ru.itis.azat.ojs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.azat.ojs.repository.UserRepository;
import ru.itis.azat.ojs.validators.UserRegistrationFormValidator;
import ru.itis.azat.ojs.forms.UserRegistrationForm;
import ru.itis.azat.ojs.model.User;
import ru.itis.azat.ojs.security.details.State;
import ru.itis.azat.ojs.service.AuthenticationService;
import ru.itis.azat.ojs.service.RegistrationService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService service;

    @Autowired
    private UserRegistrationFormValidator userRegistrationFormValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @InitBinder("userForm")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(userRegistrationFormValidator);
    }

    @GetMapping(value = "/confirm/{file-name}")
    public String confirmEmail(@PathVariable("file-name") String fileName, RedirectAttributes attributes) {
        Optional<User> optionalUser = userRepository.findByUuid(UUID.fromString(fileName));
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setState(State.CONFIRMED);
            userRepository.save(user);
            attributes.addFlashAttribute("error", "Пользователь подтверждён");
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/sign-up")
    public String signUp(@Valid @ModelAttribute("userForm") UserRegistrationForm userRegistrationForm,
                         BindingResult errors, RedirectAttributes attributes, @ModelAttribute("model") ModelMap model) {
        if (errors.hasErrors()) {
            attributes.addFlashAttribute("error", errors.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/sign-up";
        }
        service.register(userRegistrationForm);
        return "success_registration";
    }

    @GetMapping(value = "/sign-up")
    public String getSignUpPage(@ModelAttribute("model") ModelMap model) {
        return "sign_up";
    }
}
