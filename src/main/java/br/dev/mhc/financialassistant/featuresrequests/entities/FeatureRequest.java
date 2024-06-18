package br.dev.mhc.financialassistant.featuresrequests.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "FeatureRequestBuilder")
@Entity
@Table(name = "feature_requests")
public class FeatureRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "rating", nullable = false)
    private int rating;
    @Column(name = "developed", nullable = false)
    private boolean developed;
    @Column(name = "approved", nullable = false)
    private boolean approved;
    @Column(name = "active", nullable = false)
    private boolean active;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @OneToMany(mappedBy = "featureRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "languageCode")
    private Map<String, FeatureRequestTranslation> translations = new HashMap<>();

    public void addRating(boolean positive) {
        if (positive) {
            this.rating++;
        } else {
            this.rating--;
        }
    }

    public void setTranslations(Map<String, FeatureRequestTranslation> translations) {
        if (isNull(translations)) {
            this.translations = new HashMap<>();
            return;
        }
        translations.forEach((key, value) -> {
            value.setLanguageCode(key);
            value.setFeatureRequest(this);
        });
        this.translations = translations;
    }

    public void addTranslation(String languageCode, String title, String description) {
        var translation = FeatureRequestTranslation.builder()
                .languageCode(languageCode)
                .title(title)
                .description(description)
                .featureRequest(this)
                .active(true)
                .build();
        this.translations.put(languageCode, translation);
    }

}
