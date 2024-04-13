package br.dev.mhc.templatebase.security;

import br.dev.mhc.templatebase.security.dtos.TokenResponseDTO;
import br.dev.mhc.templatebase.security.services.interfaces.ILogoutService;
import br.dev.mhc.templatebase.security.services.interfaces.IRefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public record AuthController(
        IRefreshTokenService refreshTokenService,
        ILogoutService logoutService
) {

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<TokenResponseDTO> refreshToken() {
        TokenResponseDTO token = refreshTokenService.refreshToken();
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        logoutService.logout();
        return ResponseEntity.noContent().build();
    }

}
