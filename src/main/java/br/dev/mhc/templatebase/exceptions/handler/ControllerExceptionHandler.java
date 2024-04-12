package br.dev.mhc.templatebase.exceptions.handler;

import br.dev.mhc.templatebase.common.dtos.StandardErrorDTO;
import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.common.translation.TranslationKey;
import br.dev.mhc.templatebase.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Objects;

import static br.dev.mhc.templatebase.common.translation.TranslationKey.*;
import static br.dev.mhc.templatebase.common.translation.TranslationUtil.translate;
import static br.dev.mhc.templatebase.common.translation.TranslationUtil.translateHttpStatus;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> genericHandler(Exception e, HttpServletRequest request) {
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
                .message(translate(translationKey, args))
                .path(request.getRequestURI())
                .build();
    }
}
