package ru.itis.azat.ojs.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.azat.ojs.model.User;
import ru.itis.azat.ojs.security.details.Role;
import ru.itis.azat.ojs.security.details.State;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String login;
    private List<Role> role;
    private State state;
    private String email;

    public boolean hasRole(String role) {
        return this.role.stream().anyMatch(r -> r.toString().equals(role));
    }

    public static UserDto from(User user){
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .role(user.getRoles().stream().map(ru.itis.azat.ojs.model.Role::getRole).collect(Collectors.toList()))
                .state(user.getState())
                .email(user.getEmail())
                .build();
    }
}
