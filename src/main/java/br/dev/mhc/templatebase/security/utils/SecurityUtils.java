package br.dev.mhc.templatebase.security.utils;

import br.dev.mhc.templatebase.security.UserDetailsModel;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import static java.util.Objects.nonNull;

@Component
public class SecurityUtils {

    private static String SECRET_KEYWORD;

    public static UserDetailsModel getAuthenticatedUser() {
        try {
            return (UserDetailsModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getIdAuthenticatedUser() {
        UserDetailsModel userDetailsModel = getAuthenticatedUser();
        return nonNull(userDetailsModel) ? userDetailsModel.getId() : null;
    }

    public static String getUsernameAuthenticatedUser() {
        UserDetailsModel userDetailsModel = getAuthenticatedUser();
        return nonNull(userDetailsModel) ? userDetailsModel.getUsername() : null;
    }

    public static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEYWORD.getBytes());
    }

    @Value("${jwt.secret}")
    public void setSecretKeyword(String secretKeyword) {
        SECRET_KEYWORD = secretKeyword;
    }

}
