package br.dev.mhc.templatebase.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class StandardErrorDTO implements Serializable {

    private final Instant timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;
    @Builder.Default
    private final List<String> additionalInfo = new ArrayList<>();

    public void addAdditionalData(String message) {
        this.additionalInfo.add(message);
    }

    public String toJsonString() {
        return String.format("""
                        {
                        "timestamp":"%s",
                        "status":%d,
                        "error":"%s",
                        "message":"%s",
                        "path":"%s"
                        }
                        """,
                timestamp, status, error, message, path
        );
    }

}
