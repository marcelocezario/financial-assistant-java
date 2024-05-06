package br.dev.mhc.financialassistant.category.dtos;

import br.dev.mhc.financialassistant.category.annotations.CategoryDTOValidator;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@CategoryDTOValidator
public class CategoryDTO implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String iconUrl;
    private String color;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Long userId;

}
