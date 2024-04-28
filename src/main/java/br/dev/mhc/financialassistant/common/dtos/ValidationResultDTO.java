package br.dev.mhc.financialassistant.common.dtos;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResultDTO<T> implements Serializable {

    private final T object;
    private final List<FieldMessageDTO> errors = new ArrayList<>();

    public ValidationResultDTO(T object) {
        this.object = object;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessageDTO(fieldName, message));
    }

}
