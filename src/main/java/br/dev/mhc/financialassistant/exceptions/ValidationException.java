package br.dev.mhc.financialassistant.exceptions;

import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final ValidationResultDTO<?> validationResultDTO;

    public ValidationException(ValidationResultDTO<?> validationResultDTO) {
        super("Invalid data [" + validationResultDTO.getObject().getClass().getSimpleName() + "], errors " + validationResultDTO.getErrors());
        this.validationResultDTO = validationResultDTO;
    }
}
