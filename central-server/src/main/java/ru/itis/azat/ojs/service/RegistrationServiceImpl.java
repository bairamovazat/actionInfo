package ru.itis.azat.ojs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.azat.ojs.repository.RoleRepository;
import ru.itis.azat.ojs.repository.UserRepository;
import ru.itis.azat.ojs.security.details.Role;
import ru.itis.azat.ojs.security.details.State;
import ru.itis.azat.ojs.forms.UserRegistrationForm;
import ru.itis.azat.ojs.model.User;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmailService emailService;
    @Value("${site.url}")
    private String siteUrl;

    private String emailMessage = "Здравствуйте! Перейдите по ссылке, чтобы подтвердить аккаунт %s/confirm/%s";

    @Override
    @Transactional
    public void register(UserRegistrationForm userForm) {
        UUID uuid = UUID.randomUUID();
        User newUser = User.builder()
                .login(userForm.getLogin())
                .hashPassword(passwordEncoder.encode(userForm.getPassword()))
                .state(State.CREATED)
                .email(userForm.getEmail())
                .name(userForm.getName())
                .roles(new ArrayList<>())
                .uuid(uuid)
                .build();
        ru.itis.azat.ojs.model.Role role = roleRepository.findFirstByRole(Role.USER);
        role.getUsers().add(newUser);
        newUser.getRoles().add(role);
        emailService.sendMailAsync(String.format(emailMessage, siteUrl, uuid), "Подтверждение аккаунта", userForm.getEmail());
        userRepository.save(newUser);
    }
}
