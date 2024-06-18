package br.dev.mhc.financialassistant.featuresrequests.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FeatureRequestTranslationDTO implements Serializable {

    private Long id;
    private String languageCode;
    private String title;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

}
