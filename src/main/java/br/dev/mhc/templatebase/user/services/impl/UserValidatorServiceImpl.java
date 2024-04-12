package br.dev.mhc.templatebase.user.services.impl;

import br.dev.mhc.templatebase.common.dtos.ValidationResultDTO;
import br.dev.mhc.templatebase.user.UserRepository;
import br.dev.mhc.templatebase.user.annotations.UserDTOValidator;
import br.dev.mhc.templatebase.user.dtos.UserDTO;
import br.dev.mhc.templatebase.user.services.interfaces.IUserValidatorService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

import static br.dev.mhc.templatebase.common.translation.TranslationKey.*;
import static br.dev.mhc.templatebase.common.translation.TranslationUtil.translate;
import static br.dev.mhc.templatebase.common.utils.validations.ValidationUtils.*;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
public class UserValidatorServiceImpl implements IUserValidatorService, ConstraintValidator<UserDTOValidator, UserDTO> {

    private final UserRepository repository;

    public UserValidatorServiceImpl(UserRepository repository) {
        this.repository = repository;
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
        validationResult.getErrors().forEach(error -> {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(error.getMessage())
                    .addPropertyNode(error.getFieldName())
                    .addConstraintViolation();
        });
        return validationResult.isValid();
    }

    private void validatePassword(ValidationResultDTO<UserDTO> validation) {
        final var FIELD_NAME = "password";
        var password = validation.getObject().getPassword();
        final var LENGTH_MIN = 8;
        if (isNull(password)) {
            if (isNull(validation.getObject().getId())) {
                validation.addError(FIELD_NAME, translate(USER_VALIDATION_PASSWORD_CANNOT_BE_NULL));
            }
            return;
        }
        if (password.isBlank()) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_PASSWORD_CANNOT_BE_EMPTY));
        }
        if (password.length() < LENGTH_MIN) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_PASSWORD_CANNOT_BE_LESS_THEN_CHARACTERS, LENGTH_MIN));
        }
        if (!containsLetters(password)) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_LETTER));
        }
        if (!containsNumbers(password)) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_NUMBER));
        }
        if (!containsSpecialCharacters(password)) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_SPECIAL_CHARACTER));
        }
    }

    private void validateEmail(ValidationResultDTO<UserDTO> validation) {
        final var FIELD_NAME = "email";
        var email = validation.getObject().getEmail();
        final var LENGTH_MAX = 255;
        if (isNull(email)) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_EMAIL_CANNOT_BE_NULL));
            return;
        }
        if (email.isBlank()) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_EMAIL_CANNOT_BE_EMPTY));
        }
        if (!isValidEmail(email)) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_EMAIL_IS_INVALID));
        }
        if (email.length() > LENGTH_MAX) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_EMAIL_CANNOT_BE_LONGER_THEN_CHARACTERS, LENGTH_MAX));
        }
        var userOpt = repository.findByEmail(email);
        if (userOpt.isPresent()) {
            var userByEmail = userOpt.get();
            if (!userByEmail.getId().equals(validation.getObject().getId())) {
                validation.addError(FIELD_NAME, translate(USER_VALIDATION_EMAIL_IS_ALREADY_USED));
            }
        }
    }

    private void validateNickname(ValidationResultDTO<UserDTO> validation) {
        final var FIELD_NAME = "nickname";
        var nickname = validation.getObject().getNickname();
        final var LENGTH_MIN = 3;
        final var LENGTH_MAX = 255;
        if (isNull(nickname)) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_NICKNAME_CANNOT_BE_NULL));
            return;
        }
        if (nickname.isBlank()) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_NICKNAME_CANNOT_BE_EMPTY));
        }
        if (nickname.length() < LENGTH_MIN) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_NICKNAME_CANNOT_BE_LESS_THEN_CHARACTERS, LENGTH_MIN));
        }
        if (nickname.length() > LENGTH_MAX) {
            validation.addError(FIELD_NAME, translate(USER_VALIDATION_NICKNAME_CANNOT_BE_LONGER_THEN_CHARACTERS, LENGTH_MAX));
        }
    }

}
