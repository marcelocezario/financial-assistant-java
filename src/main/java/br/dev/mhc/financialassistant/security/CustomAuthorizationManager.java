package br.dev.mhc.financialassistant.security;

import br.dev.mhc.financialassistant.security.models.UserAuthenticated;
import br.dev.mhc.financialassistant.user.enums.UserRole;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext request) {
        UserAuthenticated userAuthenticated;
        try {
            userAuthenticated = (UserAuthenticated) authentication.get().getPrincipal();
        } catch (Exception e) {
            return new AuthorizationDecision(false);
        }

        if (userAuthenticated.hasRole(UserRole.ADMIN)) {
            return new AuthorizationDecision(true);
        }

        boolean isAuthorized = verifyUserId(request.getRequest().getRequestURI(), userAuthenticated);

        return new AuthorizationDecision(isAuthorized);
    }

    private boolean verifyUserId(String uri, UserAuthenticated userAuthenticated) {
        List<String> uriParts = Arrays.asList(uri.split("/"));
        boolean isAuthorized = true;
        int indexUsers = uriParts.indexOf("users");
        if (indexUsers >= 0 && indexUsers + 1 < uriParts.size()) {
            String childPath = uriParts.get(indexUsers + 1);
            UUID childId = UUID.fromString(childPath);
            isAuthorized = userAuthenticated.getId().equals(childId);
        }
        return isAuthorized;
    }
}
