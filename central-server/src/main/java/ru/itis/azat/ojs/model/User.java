package ru.itis.azat.ojs.model;

import lombok.*;
import ru.itis.azat.ojs.security.details.State;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private UUID uuid;

    @Column(unique = true)
    private String login;

    private String hashPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    private State state;

    private String email;

    public boolean hasRole(ru.itis.azat.ojs.security.details.Role role){
        return roles.stream().anyMatch(r -> r.getRole().equals(role));
    }

}