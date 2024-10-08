package br.dev.mhc.financialassistant.common.dtos;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

    public <X extends Throwable> void isValidOrThrow(Function<ValidationResultDTO<T>, ? extends X> exceptionSupplier) throws X {
        if (!isValid()) {
            throw exceptionSupplier.apply(this);
        }
    }

    public void addError(String fieldName, Object value, String message) {
        errors.add(new FieldMessageDTO(fieldName, value, message));
    }

    @Override
    public String toString() {
        return "Validation result - " +
                "type [" + object.getClass().getSimpleName() + "]" +
                ", status [" + (isValid() ? "valid" : "invalid") + "]" +
                ", errors " + errors;
    }
}
