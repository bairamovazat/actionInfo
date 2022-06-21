package ru.itis.azat.ojs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.azat.ojs.forms.TokenForm;
import ru.itis.azat.ojs.model.User;
import ru.itis.azat.ojs.security.details.State;
import ru.itis.azat.ojs.service.AuthenticationService;
import ru.itis.azat.ojs.service.TokenService;
import ru.itis.azat.ojs.transfer.TokenDto;
import ru.itis.azat.ojs.validators.TokenFormValidator;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenFormValidator tokenFormValidator;

    @InitBinder("tokenForm")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(tokenFormValidator);
    }

    @PostMapping(value = "")
    public String signUp(@Valid @ModelAttribute("tokenForm") TokenForm tokenForm,
                         BindingResult errors, RedirectAttributes attributes, @ModelAttribute("model") ModelMap model) {
        if (errors.hasErrors()) {
            attributes.addFlashAttribute("error", errors.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/token";
        }
        tokenService.createNewToken(tokenForm);

        authenticationService.putUserToModelIfExists(null, model);

        attributes.addFlashAttribute("success", "Токен отправлен на почту");
        return "redirect:/token";
    }

    @GetMapping(value = "")
    public String getTokenPage(@ModelAttribute("model") ModelMap model) {
        authenticationService.putUserToModelIfExists(null, model);
        model.addAttribute("tokens", tokenService.getTokenListForCurrentUser());
        return "token";
    }

    @DeleteMapping
    public ResponseEntity<String> deleteToken(@RequestBody TokenDto tokenDto) {
        int debug = 1;
        tokenService.deleteToken(tokenDto);
        return ResponseEntity.ok("OK");
    }
}
