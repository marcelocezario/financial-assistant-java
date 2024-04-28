package br.dev.mhc.financialassistant.common.translation;

public enum TranslationKey {

    COMPANY_NAME("company.name"),

    EMAIL_GENERIC_GREETING("email.generic.greeting"),
    EMAIL_GENERIC_COMPANY_SIGNATURE("email.generic.company.signature"),

    EMAIL_FORGOT_PASSWORD_SUBJECT("email.forgotPassword.subject"),
    EMAIL_FORGOT_PASSWORD_CONTENT("email.forgotPassword.content"),
    EMAIL_FORGOT_PASSWORD_TOKEN_EXPIRATION("email.forgotPassword.tokenExpiration"),

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

    public String translate() {
        return TranslationUtil.translate(key);
    }

    public String translate(Object... args) {
        return TranslationUtil.translate(key, args);
    }

}
