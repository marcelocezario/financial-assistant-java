package br.dev.mhc.financialassistant.exceptions;

import lombok.Getter;

import static java.util.Objects.isNull;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String term;
    private final Class<?> classType;

    public ResourceNotFoundException(Long id, Class<?> classType) {
        super("Resource not found, id [" + id.toString() + "], class name: [" + classType.getSimpleName() + "]");
        this.term = id.toString();
        this.classType = classType;
    }

    public ResourceNotFoundException(String term, Class<?> classType) {
        super("Resource not found, message: [" + term + "], class name: [" + classType.getSimpleName() + "]");
        this.term = term;
        this.classType = classType;
    }

    public String getClassTypeName() {
        if (isNull(classType)) {
            return null;
        }
        return classType.getSimpleName();
    }

}
