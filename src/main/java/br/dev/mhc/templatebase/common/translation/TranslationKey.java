package br.dev.mhc.templatebase.common.translation;

import lombok.Getter;

import java.util.Arrays;

import static java.util.Objects.isNull;

@Getter
public enum TranslationKey {

    EXCEPTION_GENERIC("exceptions.generic"),
    EXCEPTION_METHOD_ARGUMENT_NOT_VALID("exceptions.methodArgumentNotValidException"),
    EXCEPTION_RESOURCE_NOT_FOUND("exceptions.resourceNotFound"),

    USER_VALIDATION_EMAIL_CANNOT_BE_EMPTY("user.validation.email.cannotBeEmpty"),
    USER_VALIDATION_EMAIL_CANNOT_BE_LONGER_THEN_CHARACTERS("user.validation.email.cannotBeLongerThenCharacters"),
    USER_VALIDATION_EMAIL_CANNOT_BE_NULL("user.validation.email.cannotBeNull"),
    USER_VALIDATION_EMAIL_IS_ALREADY_USED("user.validation.email.isAlreadyUsed"),
    USER_VALIDATION_EMAIL_IS_INVALID("user.validation.email.isInvalid"),
    USER_VALIDATION_NICKNAME_CANNOT_BE_EMPTY("user.validation.nickname.cannotBeEmpty"),
    USER_VALIDATION_NICKNAME_CANNOT_BE_LESS_THEN_CHARACTERS("user.validation.nickname.cannotBeLessThenCharacters"),
    USER_VALIDATION_NICKNAME_CANNOT_BE_LONGER_THEN_CHARACTERS("user.validation.nickname.cannotBeLongerThenCharacters"),
    USER_VALIDATION_NICKNAME_CANNOT_BE_NULL("user.validation.nickname.cannotBeNull"),
    USER_VALIDATION_PASSWORD_CANNOT_BE_EMPTY("user.validation.password.cannotBeEmpty"),
    USER_VALIDATION_PASSWORD_CANNOT_BE_LESS_THEN_CHARACTERS("user.validation.password.cannotBeLessThenCharacters"),
    USER_VALIDATION_PASSWORD_CANNOT_BE_NULL("user.validation.password.cannotBeNull"),
    USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_LETTER("user.validation.password.mustContainAtLeastOneLetter"),
    USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_NUMBER("user.validation.password.mustContainAtLeastOneNumber"),
    USER_VALIDATION_PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_SPECIAL_CHARACTER("user.validation.password.mustContainAtLeastOneSpecialCharacter"),
    ;

    private final String key;

    TranslationKey(String key) {
        this.key = key;
    }

    public static TranslationKey toEnum(String key) {
        if (isNull(key)) return null;
        return Arrays.stream(TranslationKey.values())
                .filter(objEnum -> objEnum.getKey().equals(key))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return key;
    }
}
