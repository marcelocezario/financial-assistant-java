package br.dev.mhc.financialassistant.featuresrequests.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FeatureRequestDTO implements Serializable {

    private Long id;
    private String description;
    private int rating;
    private boolean developed;
    private boolean approved;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    @Builder.Default
    private Map<String, FeatureRequestTranslationDTO> translations = new HashMap<>();

}
