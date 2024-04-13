package br.dev.mhc.templatebase.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public record AuthController(
//        ILogoutService logoutService,
//        IGenerateRefreshTokenService refreshTokenService
) {

//    @PostMapping(value = "/logout")
//    public ResponseEntity<Void> logout() {
//        logoutService.logout();
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping(value = "/refresh-token")
//    public ResponseEntity<TokenResponseDTO> refreshToken() {
//        TokenResponseDTO token = refreshTokenService.refreshToken();
//        return ResponseEntity.ok(token);
//    }

}
