package br.dev.mhc.financialassistant.security;

import br.dev.mhc.financialassistant.user.User;
import br.dev.mhc.financialassistant.user.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserAuthenticated implements UserDetails {

    private final int MAX_FAILED_LOGIN_ATTEMPTS = 3;

    @Getter
    private final long id;
    private final String username;
    private final String password;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;
    private final int failedLoginAttempts;

    private UserAuthenticated(UserDetailsModelBuilder builder) {
        super();
        id = builder.user.getId();
        username = builder.user.getEmail();
        password = builder.user.getPassword();
        active = builder.user.isActive();
        authorities = builder.user.getRoles().stream().map(p -> new SimpleGrantedAuthority("ROLE_" + p.getDescription())).toList();
        failedLoginAttempts = builder.failedLoginAttempts;
    }

    public static UserDetailsModelBuilder builder() {
        return new UserAuthenticated.UserDetailsModelBuilder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return failedLoginAttempts < MAX_FAILED_LOGIN_ATTEMPTS;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public boolean hasRole(UserRole role) {
        return getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role.getDescription()));
    }

    @NoArgsConstructor
    public static class UserDetailsModelBuilder {

        User user;
        int failedLoginAttempts = 0;

        public UserDetailsModelBuilder user(User user) {
            this.user = user;
            return this;
        }

        public UserDetailsModelBuilder failedLoginAttempts(int failedLoginAttempts) {
            this.failedLoginAttempts = failedLoginAttempts;
            return this;
        }

        public UserAuthenticated build() {
            return new UserAuthenticated(this);
        }
    }
}