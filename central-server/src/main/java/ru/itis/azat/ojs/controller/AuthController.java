package ru.itis.azat.ojs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.azat.ojs.model.User;
import ru.itis.azat.ojs.security.details.Role;
import ru.itis.azat.ojs.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationService service;

    @GetMapping("/login")
    public String login(@ModelAttribute("model") ModelMap model, Authentication authentication,
                        @RequestParam Optional<String> error) {

        if (authentication != null) {
            return "redirect:/";
        }

        model.addAttribute("error", error);
        service.putUserToModelIfExists(authentication, model);

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Authentication authentication) {
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication != null) {
            User user = service.getUserByAuthentication(authentication);
            if (user.hasRole(Role.USER)) {
                return "redirect:/profile";
            } else if (user.hasRole(Role.ADMIN)) {
                return "redirect:/admin/users";
            }
        }
        return "redirect:/login";
    }


}