package br.dev.mhc.financialassistant.security;

import br.dev.mhc.financialassistant.security.dtos.ForgotPasswordRequestDTO;
import br.dev.mhc.financialassistant.security.dtos.TokenResponseDTO;
import br.dev.mhc.financialassistant.security.services.interfaces.IForgotPasswordService;
import br.dev.mhc.financialassistant.security.services.interfaces.ILogoutService;
import br.dev.mhc.financialassistant.security.services.interfaces.IRefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public record AuthController(
        IRefreshTokenService refreshTokenService,
        ILogoutService logoutService,
        IForgotPasswordService forgotPasswordService
) {

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<TokenResponseDTO> refreshToken() {
        var token = refreshTokenService.refreshToken();
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        logoutService.logout();
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequestDTO forgotPassword) {
        forgotPasswordService.forgotPassword(forgotPassword);
        return ResponseEntity.noContent().build();
    }

}
