package ru.itis.azat.ojs.service;

import ru.itis.azat.ojs.model.User;
import ru.itis.azat.ojs.security.details.Role;
import ru.itis.azat.ojs.security.details.State;
import ru.itis.azat.ojs.transfer.UserDto;

import java.util.List;


public interface AdminService {
    List<User> getAllUsers();

    List<User> getNonConfirmedUsers();

    User changeUserStatus(Long userId, State state);

    User changeUserRole(Long userId, List<Role> role);
}
