package br.dev.mhc.templatebase.common.dtos;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FieldMessageDTO implements Serializable {

    private String fieldName;
    private String message;

}
