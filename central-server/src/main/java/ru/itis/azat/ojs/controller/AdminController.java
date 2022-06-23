package ru.itis.azat.ojs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itis.azat.ojs.service.AdminService;
import ru.itis.azat.ojs.service.AuthenticationService;
import ru.itis.azat.ojs.transfer.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/user-confirm")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<UserDto> getAllUser()
    {
        return adminService.getAllUsers().stream().map(UserDto::from).collect(Collectors.toList());
    }

    @PutMapping("/user-confirm/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public UserDto changeUserStatus(@PathVariable Long userId, @RequestBody UserDto
                                    userDto) {
        return UserDto.from(adminService.changeUserStatus(userId, userDto.getState()));
    }

    @PutMapping("/user-role/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public UserDto changeUserRole(@PathVariable Long userId, @RequestBody UserDto
            userDto) {
        return UserDto.from(adminService.changeUserRole(userId, userDto.getRole()));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserConfirmPage(Authentication authentication, @ModelAttribute("model") ModelMap model) {
        authenticationService.putUserToModelIfExists(authentication, model);
        return "admin/user_request_page";
    }

    @GetMapping("/rest-client")
    @PreAuthorize("hasRole('ADMIN')")
    public String getRestClient(Authentication authentication, @ModelAttribute("model") ModelMap model) {
        authenticationService.putUserToModelIfExists(authentication, model);
        return "rest/rest-client";
    }
}
