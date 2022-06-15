package ru.itis.azat.ojs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.azat.ojs.service.AuthenticationService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping
    public String getProfilePage(Authentication authentication, @ModelAttribute("model") ModelMap model) {
        authenticationService.putUserToModelIfExists(authentication, model);
        return "profile/profile";
    }
}
