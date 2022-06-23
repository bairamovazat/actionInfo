package ru.itis.azat.ojs.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.azat.ojs.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList =  user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole().toString()))
                .collect(Collectors.toList());
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getHashPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.getState().equals(State.CONFIRMED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.getState().equals(State.CONFIRMED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getState().equals(State.CONFIRMED);
    }

    public User getUser() {
        return user;
    }
}