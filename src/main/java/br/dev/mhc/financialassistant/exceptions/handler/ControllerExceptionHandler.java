package br.dev.mhc.financialassistant.exceptions.handler;

import br.dev.mhc.financialassistant.common.dtos.FieldMessageDTO;
import br.dev.mhc.financialassistant.common.dtos.StandardErrorDTO;
import br.dev.mhc.financialassistant.common.logs.LogHelper;
import br.dev.mhc.financialassistant.common.translation.TranslationKey;
import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Objects;

import static br.dev.mhc.financialassistant.common.translation.TranslationKey.*;
import static br.dev.mhc.financialassistant.common.translation.TranslationUtil.translateHttpStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final LogHelper LOG = new LogHelper(ControllerExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var error =
                buildStandardError(request, status, EXCEPTION_RESOURCE_NOT_FOUND, e.getTerm(), e.getClassTypeName());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> validationError(MethodArgumentNotValidException e,
                                                            HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        var error = buildStandardError(request, status, EXCEPTION_METHOD_ARGUMENT_NOT_VALID);
        e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .forEach(error::addAdditionalData);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(AppValidationException.class)
    public ResponseEntity<StandardErrorDTO> appValidation(AppValidationException e,
                                                          HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        var error = buildStandardError(request, status, EXCEPTION_METHOD_ARGUMENT_NOT_VALID);
        e.getValidationResultDTO().getErrors()
                .stream()
                .map(FieldMessageDTO::getMessage)
                .filter(Objects::nonNull)
                .forEach(error::addAdditionalData);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardErrorDTO> dataIntegrityViolationException(DataIntegrityViolationException e,
                                                                            HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        var error = buildStandardError(request, status, EXCEPTION_DATA_INTEGRITY_VIOLATION);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> genericHandler(Exception e, HttpServletRequest request) {
        if (e.getCause() instanceof ResourceNotFoundException) {
            return this.resourceNotFound((ResourceNotFoundException) e.getCause(), request);
        }
        var status = HttpStatus.BAD_REQUEST;
        var error = buildStandardError(request, status, EXCEPTION_GENERIC);
        LOG.stackTrace("An unexpected error has occurred", e);
        return ResponseEntity.status(status).body(error);
    }

    private StandardErrorDTO buildStandardError(HttpServletRequest request, HttpStatus httpStatus, TranslationKey translationKey, Object... args) {
        return StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .error(translateHttpStatus(httpStatus))
                .message(translationKey.translate(args))
                .path(request.getRequestURI())
                .build();
    }
}
