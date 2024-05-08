package br.dev.mhc.financialassistant.user.services.impl;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.user.annotations.UserDTOValidator;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import br.dev.mhc.financialassistant.user.services.interfaces.IUserValidatorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.common.translation.TranslationKey.*;
import static br.dev.mhc.financialassistant.common.utils.validations.ValidationUtils.*;
import static java.util.Objects.*;

@Service
public class UserValidatorServiceImpl implements IUserValidatorService, ConstraintValidator<UserDTOValidator, UserDTO> {

    private final UserRepository repository;
    private final HttpServletRequest request;

    public UserValidatorServiceImpl(UserRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    @Override
    public ValidationResultDTO<UserDTO> validate(UserDTO userDTO) {
        requireNonNull(userDTO);
        var validation = new ValidationResultDTO<>(userDTO);

        validateNickname(validation);
        validateEmail(validation);
        validatePassword(validation);

        return validation;
    }

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext constraintValidatorContext) {
        var validationResult = validate(userDTO);
        validateRoute(validationResult);
        validationResult.getErrors().forEach(error -> {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(error.getMessage())
                    .addPropertyNode(error.getFieldName())
                    .addConstraintViolation();
        });
        return validationResult.isValid();
    }

    private void validateRoute(ValidationResultDTO<UserDTO> validationResult) {
        var uri = request.getRequestURI();
        var userDTO = validationResult.getObject();
        var userId = URIUtils.findUuidAfterPath(uri, RouteConstants.USERS_ROUTE);
        if (nonNull(userDTO.getId()) && !userDTO.getId().equals(userId)) {
            validationResult.addError("id", userDTO.getId(), USER_VALIDATION_ID_DOES_NOT_MATCH_ROUTE.translate());
        }
    }

    private void validatePassword(ValidationResultDTO<UserDTO> validation) {
        final var FIELD_NAME = "password";
        var password = validation.getObject().getPassword();
        final var LENGTH_MIN = 8;
        if (isNull(password)) {
            if (isNull(validation.getObject().getId())) {
                validation.addError(FIELD_NAME, null, USER_VALIDATION_PASSWORD_CANNOT_BE_NULL.translate());
            }
            return;
        }
        if (password.isBlank()) {
            validation.addError(FIELD_NAME, password, USER_VALIDATION_PASSWORD_CANNOT_BE_EMPTY.translate());
        }
        if (password.length() < LENGTH_MIN) {
            validation.addError(FIELD_NAME, password, USER_VALIDATION_PASSWORD_CANNOT_BE_LESS_THEN_CHARACTERS.translate(LENGTH_MIN));
        }
        if (!containsLetters(password)) {
            validation.addError(FIELD_NAME, password, USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_LETTER.translate());
        }
        if (!containsNumbers(password)) {
            validation.addError(FIELD_NAME, password, USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_NUMBER.translate());
        }
        if (!containsSpecialCharacters(password)) {
            validation.addError(FIELD_NAME, password, USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_SPECIAL_CHARACTER.translate());
        }
    }

    private void validateEmail(ValidationResultDTO<UserDTO> validation) {
        final var FIELD_NAME = "email";
        var email = validation.getObject().getEmail();
        final var LENGTH_MAX = 255;
        if (isNull(email)) {
            validation.addError(FIELD_NAME, null, USER_VALIDATION_EMAIL_CANNOT_BE_NULL.translate());
            return;
        }
        if (email.isBlank()) {
            validation.addError(FIELD_NAME, email, USER_VALIDATION_EMAIL_CANNOT_BE_EMPTY.translate());
        }
        if (!isValidEmail(email)) {
            validation.addError(FIELD_NAME, email, USER_VALIDATION_EMAIL_IS_INVALID.translate());
        }
        if (email.length() > LENGTH_MAX) {
            validation.addError(FIELD_NAME, email, USER_VALIDATION_EMAIL_CANNOT_BE_LONGER_THEN_CHARACTERS.translate(LENGTH_MAX));
        }
        var userOpt = repository.findByEmailIgnoreCase(email);
        if (userOpt.isPresent()) {
            var userByEmail = userOpt.get();
            if (!userByEmail.getId().equals(validation.getObject().getId())) {
                validation.addError(FIELD_NAME, email, USER_VALIDATION_EMAIL_IS_ALREADY_USED.translate());
            }
        }
    }

    private void validateNickname(ValidationResultDTO<UserDTO> validation) {
        final var FIELD_NAME = "nickname";
        var nickname = validation.getObject().getNickname();
        final var LENGTH_MIN = 3;
        final var LENGTH_MAX = 255;
        if (isNull(nickname)) {
            validation.addError(FIELD_NAME, null, USER_VALIDATION_NICKNAME_CANNOT_BE_NULL.translate());
            return;
        }
        if (nickname.isBlank()) {
            validation.addError(FIELD_NAME, nickname, USER_VALIDATION_NICKNAME_CANNOT_BE_EMPTY.translate());
        }
        if (nickname.length() < LENGTH_MIN) {
            validation.addError(FIELD_NAME, nickname, USER_VALIDATION_NICKNAME_CANNOT_BE_LESS_THEN_CHARACTERS.translate(LENGTH_MIN));
        }
        if (nickname.length() > LENGTH_MAX) {
            validation.addError(FIELD_NAME, nickname, USER_VALIDATION_NICKNAME_CANNOT_BE_LONGER_THEN_CHARACTERS.translate(LENGTH_MAX));
        }
    }

}
