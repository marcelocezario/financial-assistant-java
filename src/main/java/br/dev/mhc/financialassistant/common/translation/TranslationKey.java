package br.dev.mhc.financialassistant.common.translation;

public enum TranslationKey {

    CATEGORY_VALIDATION_COLOR_IS_INVALID("category.validation.color.isInvalid"),
    CATEGORY_VALIDATION_COLOR_MUST_HAVE_CHARACTERS("category.validation.color.mustHaveCharacters"),
    CATEGORY_VALIDATION_ICON_URL_CANNOT_BE_LONGER_THEN_CHARACTERS("category.validation.iconUrl.cannotBeLongerThenCharacters"),
    CATEGORY_VALIDATION_NAME_CANNOT_BE_EMPTY("category.validation.name.cannotBeEmpty"),
    CATEGORY_VALIDATION_NAME_CANNOT_BE_LESS_THEN_CHARACTERS("category.validation.name.cannotBeLessThenCharacters"),
    CATEGORY_VALIDATION_NAME_CANNOT_BE_LONGER_THEN_CHARACTERS("category.validation.name.cannotBeLongerThenCharacters"),
    CATEGORY_VALIDATION_NAME_CANNOT_BE_NULL("category.validation.name.cannotBeNull"),
    CATEGORY_VALIDATION_NAME_IS_ALREADY_USED_BY_USER("category.validation.name.isAlreadyUsedByUser"),
    CATEGORY_VALIDATION_USER_ID_CANNOT_BE_NULL("category.validation.userId.cannotBeNull"),
    CATEGORY_VALIDATION_USER_ID_DOES_NOT_EXIST("category.validation.userId.doesNotExist"),
    CATEGORY_VALIDATION_USER_ID_UNAUTHORIZED("category.validation.userId.unauthorized"),

    COMPANY_NAME("company.name"),

    CURRENCY_VALIDATION_CODE_CANNOT_BE_EMPTY("currency.validation.code.cannotBeEmpty"),
    CURRENCY_VALIDATION_CODE_CANNOT_BE_NULL("currency.validation.code.cannotBeNull"),
    CURRENCY_VALIDATION_CODE_IS_ALREADY_USED("currency.validation.code.isAlreadyUsedByUser"),
    CURRENCY_VALIDATION_CODE_MUST_HAVE_CHARACTERS("currency.validation.code.mustHaveCharacters"),
    CURRENCY_VALIDATION_NAME_CANNOT_BE_EMPTY("currency.validation.name.cannotBeEmpty"),
    CURRENCY_VALIDATION_NAME_CANNOT_BE_LESS_THEN_CHARACTERS("currency.validation.name.cannotBeLessThenCharacters"),
    CURRENCY_VALIDATION_NAME_CANNOT_BE_LONGER_THEN_CHARACTERS("currency.validation.name.cannotBeLongerThenCharacters"),
    CURRENCY_VALIDATION_NAME_CANNOT_BE_NULL("currency.validation.name.cannotBeNull"),
    CURRENCY_VALIDATION_NAME_IS_ALREADY_USED("currency.validation.name.isAlreadyUsedByUser"),
    CURRENCY_VALIDATION_PRICE_IN_BRL_CANNOT_BE_NULL("currency.validation.princeInBRL.cannotBeNull"),
    CURRENCY_VALIDATION_PRICE_IN_BRL_MUST_BE_GREATER_THAN_ZERO("currency.validation.princeInBRL.mustBeGreaterThanZero"),
    CURRENCY_VALIDATION_SYMBOL_CANNOT_BE_EMPTY("currency.validation.symbol.cannotBeEmpty"),
    CURRENCY_VALIDATION_SYMBOL_CANNOT_BE_LESS_THEN_CHARACTERS("currency.validation.symbol.cannotBeLessThenCharacters"),
    CURRENCY_VALIDATION_SYMBOL_CANNOT_BE_LONGER_THEN_CHARACTERS("currency.validation.symbol.cannotBeLongerThenCharacters"),
    CURRENCY_VALIDATION_SYMBOL_CANNOT_BE_NULL("currency.validation.symbol.cannotBeNull"),

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
