package ru.itis.azat.ojs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.azat.ojs.repository.RoleRepository;
import ru.itis.azat.ojs.repository.UserRepository;
import ru.itis.azat.ojs.model.User;
import ru.itis.azat.ojs.security.details.Role;
import ru.itis.azat.ojs.security.details.State;
import ru.itis.azat.ojs.utils.PasswordGenerator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<User> getNonConfirmedUsers() {
        return userRepository.findAllByState(State.CREATED);
    }

    @Override
    public User changeUserStatus(Long userId, State state) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.setState(state);
            userRepository.save(user);
        }

        return user;
    }

    @Override
    @Transactional
    public User changeUserRole(Long userId, List<Role> role) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {

            List<ru.itis.azat.ojs.model.Role> roles = role.stream().map(r -> roleRepository.findFirstByRole(r))
                    .collect(Collectors.toList());

            List<ru.itis.azat.ojs.model.Role> deleteRoles = user.getRoles().stream()
                    .filter(oldRole -> !roles.contains(oldRole))
                    .collect(Collectors.toList());

            user.getRoles().removeAll(deleteRoles);

            deleteRoles.forEach(r -> r.getUsers().remove(user));

            List<ru.itis.azat.ojs.model.Role> addRoles = roles.stream()
                    .filter(newRole -> !user.getRoles().contains(newRole))
                    .collect(Collectors.toList());


            addRoles.forEach(r -> {
                r.getUsers().add(user);
                user.getRoles().add(r);
            });

        }

        return user;
    }
}
