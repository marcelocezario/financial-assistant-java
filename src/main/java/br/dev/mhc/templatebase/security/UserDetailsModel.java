package br.dev.mhc.templatebase.security;

import br.dev.mhc.templatebase.user.User;
import br.dev.mhc.templatebase.user.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsModel implements UserDetails {

    @Getter
    private final long id;
    private final String username;
    private final String password;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean locked;

    private UserDetailsModel(UserDetailsModelBuilder builder) {
        super();
        id = builder.user.getId();
        username = builder.user.getEmail();
        password = builder.user.getPassword();
        active = builder.user.isActive();
        authorities = builder.user.getRoles().stream().map(p -> new SimpleGrantedAuthority("ROLE_" + p.getDescription())).toList();
        locked = builder.locked;
    }

    public static UserDetailsModelBuilder builder() {
        return new UserDetailsModel.UserDetailsModelBuilder();
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
        return !locked;
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
        boolean locked;

        public UserDetailsModelBuilder user(User user) {
            this.user = user;
            return this;
        }

        public UserDetailsModelBuilder locked(boolean locked) {
            this.locked = locked;
            return this;
        }

        public UserDetailsModel build() {
            return new UserDetailsModel(this);
        }
    }
}