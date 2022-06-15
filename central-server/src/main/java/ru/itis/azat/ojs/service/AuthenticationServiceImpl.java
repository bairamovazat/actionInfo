package ru.itis.azat.ojs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ru.itis.azat.ojs.repository.UserRepository;
import ru.itis.azat.ojs.security.details.UserDetailsImpl;
import ru.itis.azat.ojs.transfer.UserDto;
import ru.itis.azat.ojs.model.User;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserByAuthentication(authentication);
    }

    @Override
    public User getUserByAuthentication(Authentication authentication) {
        if(authentication == null){
            return null;
        }
        UserDetailsImpl currentUserDetails = (UserDetailsImpl)authentication.getPrincipal();
        User currentUserModel = currentUserDetails.getUser();
        Long currentUserId = currentUserModel.getId();
        return userRepository.findById(currentUserId).orElse(null);
    }

    @Override
    public void putUserToModelIfExists(Authentication authentication, ModelMap model) {
        if(authentication == null){
            model.addAttribute("user", Optional.empty());
            return;
        }
        model.addAttribute("user", Optional.of(UserDto.from(getUserByAuthentication(authentication))));
    }
}
