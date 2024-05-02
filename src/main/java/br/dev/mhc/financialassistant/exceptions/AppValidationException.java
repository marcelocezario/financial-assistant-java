package br.dev.mhc.financialassistant.exceptions;

import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import lombok.Getter;

@Getter
public class AppValidationException extends RuntimeException {

    private final ValidationResultDTO<?> validationResultDTO;

    public AppValidationException(ValidationResultDTO<?> validationResultDTO) {
        super("Invalid data [" + validationResultDTO.getObject().getClass().getSimpleName() + "], errors " + validationResultDTO.getErrors());
        this.validationResultDTO = validationResultDTO;
    }
}
