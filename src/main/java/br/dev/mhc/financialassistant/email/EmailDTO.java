package br.dev.mhc.financialassistant.email;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailDTO implements Serializable {

    private String from;
    @Singular("to")
    private Set<String> to = new HashSet<>();
    @Singular("cc")
    private Set<String> cc = new HashSet<>();
    @Singular("bcc")
    private Set<String> bcc = new HashSet<>();
    private String subject;
    private String body;
    @Builder.Default
    private Instant sentDate = Instant.now();
    private String pathHtmlTemplate;
    @Singular("htmlAttribute")
    private Map<String, Object> htmlAttributes = new HashMap<>();

    public boolean isHtml() {
        return nonNull(pathHtmlTemplate) && !pathHtmlTemplate.isBlank();
    }

}
