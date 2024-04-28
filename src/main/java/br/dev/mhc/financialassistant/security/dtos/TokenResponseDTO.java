package br.dev.mhc.financialassistant.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TokenResponseDTO implements Serializable {

    private final String access_token;
    private final String refresh_token;
    private final String token_type;
    private final long expires_in;
    private final long created_at;
    @Builder.Default
    private final List<String> userPendingIssues = new ArrayList<>();

}
