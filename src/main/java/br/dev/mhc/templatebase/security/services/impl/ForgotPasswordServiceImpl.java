package br.dev.mhc.templatebase.security.services.impl;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.email.EmailDTO;
import br.dev.mhc.templatebase.email.services.interfaces.ISendEmailService;
import br.dev.mhc.templatebase.security.UserAuthenticated;
import br.dev.mhc.templatebase.security.dtos.ForgotPasswordRequestDTO;
import br.dev.mhc.templatebase.security.services.interfaces.IBuildTokenService;
import br.dev.mhc.templatebase.security.services.interfaces.IForgotPasswordService;
import br.dev.mhc.templatebase.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static br.dev.mhc.templatebase.common.translation.TranslationKey.*;
import static br.dev.mhc.templatebase.security.TokenUsageType.FORGOT_PASSWORD_TOKEN;
import static java.util.Objects.requireNonNull;

@Service
public class ForgotPasswordServiceImpl implements IForgotPasswordService {

    private static final LogHelper LOG = new LogHelper(ForgotPasswordServiceImpl.class);
    private final UserRepository userRepository;
    private final ISendEmailService sendEmailService;
    private final IBuildTokenService buildTokenService;
    @Value("${jwt.expiration.forgotPasswordToken}")
    private Long EXPIRATION_FORGOT_PASSWORD_TOKEN;
    @Value("${app.url.frontend}")
    private String FRONT_END_URL;

    public ForgotPasswordServiceImpl(UserRepository userRepository, ISendEmailService sendEmailService, IBuildTokenService buildTokenService) {
        this.userRepository = userRepository;
        this.sendEmailService = sendEmailService;
        this.buildTokenService = buildTokenService;
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        requireNonNull(forgotPasswordRequestDTO);
        requireNonNull(forgotPasswordRequestDTO.getUsername());

        var userOpt = userRepository.findByEmail(forgotPasswordRequestDTO.getUsername());
        if (userOpt.isEmpty()) {
            LOG.debug("Username not found", forgotPasswordRequestDTO.getUsername());
            return;
        }
        var userDetails = UserAuthenticated.builder().user(userOpt.get()).build();
        var iat = Instant.now();
        var token = buildTokenService.build(userDetails, iat, EXPIRATION_FORGOT_PASSWORD_TOKEN, FORGOT_PASSWORD_TOKEN);
        var forgotLink = UriComponentsBuilder
                .fromHttpUrl(FRONT_END_URL)
                .path("auth/forgot-password")
                .queryParam("t", token)
                .build().toUriString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy HH:mm ('GMT'z)").withZone(ZoneId.of("-03:00"));
        String body = EMAIL_GENERIC_GREETING.translate(userOpt.get().getNickname()) +
                "\n\n" +
                EMAIL_FORGOT_PASSWORD_CONTENT.translate(forgotLink) +
                "\n" +
                EMAIL_FORGOT_PASSWORD_TOKEN_EXPIRATION.translate(formatter.format(iat.plusMillis(EXPIRATION_FORGOT_PASSWORD_TOKEN))) +
                "\n\n" +
                EMAIL_GENERIC_COMPANY_SIGNATURE.translate(COMPANY_NAME.translate());

        var email = EmailDTO.builder()
                .to(userOpt.get().getEmail())
                .subject(EMAIL_FORGOT_PASSWORD_SUBJECT.translate())
                .body(body)
                .build();
        sendEmailService.send(email);
    }
}
