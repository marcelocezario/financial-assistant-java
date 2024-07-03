package br.dev.mhc.financialassistant.common.translation;

public enum TranslationKey {
    CATEGORY_VALIDATION_CATEGORY_PARENT_ID_CANNOT_BE_A_PARENT_CATEGORY("category.validation.categoryParentId.cannotBeAParentCategory"),
    CATEGORY_VALIDATION_CATEGORY_PARENT_ID_DOES_NOT_ACTIVE("category.validation.categoryParentId.doesNotActive"),
    CATEGORY_VALIDATION_CATEGORY_PARENT_ID_DOES_NOT_EXIST("category.validation.categoryParentId.doesNotExist"),
    CATEGORY_VALIDATION_COLOR_IS_INVALID("category.validation.color.isInvalid"),
    CATEGORY_VALIDATION_COLOR_MUST_HAVE_CHARACTERS("category.validation.color.mustHaveCharacters"),
    CATEGORY_VALIDATION_ICON_CANNOT_BE_LONGER_THEN_CHARACTERS("category.validation.icon.cannotBeLongerThenCharacters"),
    CATEGORY_VALIDATION_ID_DOES_NOT_MATCH_ROUTE("category.validation.id.doesNotMatchRoute"),
    CATEGORY_VALIDATION_NAME_CANNOT_BE_EMPTY("category.validation.name.cannotBeEmpty"),
    CATEGORY_VALIDATION_NAME_CANNOT_BE_LESS_THEN_CHARACTERS("category.validation.name.cannotBeLessThenCharacters"),
    CATEGORY_VALIDATION_NAME_CANNOT_BE_LONGER_THEN_CHARACTERS("category.validation.name.cannotBeLongerThenCharacters"),
    CATEGORY_VALIDATION_NAME_CANNOT_BE_NULL("category.validation.name.cannotBeNull"),
    CATEGORY_VALIDATION_NAME_IS_ALREADY_USED_BY_USER("category.validation.name.isAlreadyUsedByUser"),
    CATEGORY_VALIDATION_TYPE_CANNOT_BE_NULL("category.validation.type.cannotBeNull"),
    CATEGORY_VALIDATION_USER_ID_CANNOT_BE_NULL("category.validation.userId.cannotBeNull"),
    CATEGORY_VALIDATION_USER_ID_DOES_NOT_EXIST("category.validation.userId.doesNotExist"),
    CATEGORY_VALIDATION_USER_ID_DOES_NOT_MATCH_ROUTE("category.validation.userId.doesNotMatchRoute"),
    CATEGORY_VALIDATION_USER_ID_UNAUTHORIZED("category.validation.userId.unauthorized"),

    COMPANY_NAME("company.name"),

    CURRENCY_VALIDATION_CODE_CANNOT_BE_EMPTY("currency.validation.code.cannotBeEmpty"),
    CURRENCY_VALIDATION_CODE_CANNOT_BE_NULL("currency.validation.code.cannotBeNull"),
    CURRENCY_VALIDATION_CODE_IS_ALREADY_USED("currency.validation.code.isAlreadyUsedByUser"),
    CURRENCY_VALIDATION_CODE_MUST_HAVE_CHARACTERS("currency.validation.code.mustHaveCharacters"),
    CURRENCY_VALIDATION_ID_DOES_NOT_MATCH_ROUTE("currency.validation.id.doesNotMatchRoute"),
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

    EMAIL_FORGOT_PASSWORD_CONTENT("email.forgotPassword.content"),
    EMAIL_FORGOT_PASSWORD_SUBJECT("email.forgotPassword.subject"),
    EMAIL_FORGOT_PASSWORD_TOKEN_EXPIRATION("email.forgotPassword.tokenExpiration"),
    EMAIL_GENERIC_COMPANY_SIGNATURE("email.generic.company.signature"),
    EMAIL_GENERIC_GREETING("email.generic.greeting"),

    EXCEPTION_DATA_INTEGRITY_VIOLATION("exceptions.dataIntegrityViolation"),
    EXCEPTION_GENERIC("exceptions.generic"),
    EXCEPTION_METHOD_ARGUMENT_NOT_VALID("exceptions.methodArgumentNotValid"),
    EXCEPTION_RESOURCE_NOT_FOUND("exceptions.resourceNotFound"),

    TRANSACTION_VALIDATION_AMOUNT_CANNOT_BE_NEGATIVE("transaction.validation.amount.cannotBeNegative"),
    TRANSACTION_VALIDATION_AMOUNT_CANNOT_BE_NULL("transaction.validation.amount.cannotBeNull"),
    TRANSACTION_VALIDATION_CATEGORIES_AMOUNT_MUST_BE_GREATER_THAN_ZERO("transaction.validation.categories.amountMustBeGreaterThanZero"),
    TRANSACTION_VALIDATION_CATEGORIES_CANNOT_BE_EMPTY("transaction.validation.categories.cannotBeEmpty"),
    TRANSACTION_VALIDATION_CATEGORIES_CANNOT_BE_NULL("transaction.validation.categories.cannotBeNull"),
    TRANSACTION_VALIDATION_CATEGORIES_CATEGORY_ID_DOES_NOT_EXIST("transaction.validation.categories.categoryIdDoesNotExist"),
    TRANSACTION_VALIDATION_CATEGORIES_CONTAINS_CATEGORIES_WITHOUT_AMOUNT("transaction.validation.categories.containsCategoriesWithoutAmount"),
    TRANSACTION_VALIDATION_CATEGORIES_CONTAINS_CATEGORIES_WITHOUT_CATEGORY_ID("transaction.validation.categories.containsCategoriesWithoutCategoryId"),
    TRANSACTION_VALIDATION_CATEGORIES_CONTAINS_DUPLICATE_OBJECTS("transaction.validation.categories.containsDuplicateObjects"),
    TRANSACTION_VALIDATION_CATEGORIES_CONTAINS_NULL_OBJECTS("transaction.validation.categories.containsNullObjects"),
    TRANSACTION_VALIDATION_CATEGORIES_SUM_DOES_NOT_MATCH_WITH_TOTAL("transaction.validation.categories.sumDoesNotMatchWithTotal"),
    TRANSACTION_VALIDATION_CURRENT_INSTALLMENT_CANNOT_BE_NULL("transaction.validation.currentInstallment.cannotBeNull"),
    TRANSACTION_VALIDATION_CURRENT_INSTALLMENT_MUST_BE_GREATER_THAN_ZERO("transaction.validation.currentInstallment.mustBeGreaterThanZero"),
    TRANSACTION_VALIDATION_ID_DOES_NOT_MATCH_ROUTE("transaction.validation.id.doesNotMatchRoute"),
    TRANSACTION_VALIDATION_METHOD_CANNOT_BE_NULL("transaction.validation.method.cannotBeNull"),
    TRANSACTION_VALIDATION_METHOD_IS_NOT_ACCEPTED_BY_WALLET("transaction.validation.method.isNotAcceptedByWallet"),
    TRANSACTION_VALIDATION_DUE_DATE_CANNOT_BE_NULL("transaction.validation.dueDate.cannotBeNull"),
    TRANSACTION_VALIDATION_PAYMENT_MOMENT_CANNOT_BE_BEFORE_DATE("transaction.validation.paymentMoment.cannotBeBeforeDate"),
    TRANSACTION_VALIDATION_NOTES_CANNOT_BE_LONGER_THEN_CHARACTERS("transaction.validation.notes.cannotBeLongerThenCharacters"),
    TRANSACTION_VALIDATION_TYPE_CANNOT_BE_NULL("transaction.validation.type.cannotBeNull"),
    TRANSACTION_VALIDATION_USER_ID_CANNOT_BE_NULL("transaction.validation.userId.cannotBeNull"),
    TRANSACTION_VALIDATION_USER_ID_DOES_NOT_EXIST("transaction.validation.userId.doesNotExist"),
    TRANSACTION_VALIDATION_USER_ID_DOES_NOT_MATCH_ROUTE("transaction.validation.userId.doesNotMatchRoute"),
    TRANSACTION_VALIDATION_USER_ID_UNAUTHORIZED("transaction.validation.userId.unauthorized"),
    TRANSACTION_VALIDATION_WALLET_CANNOT_BE_NULL("transaction.validation.wallet.cannotBeNull"),
    TRANSACTION_VALIDATION_WALLET_DOES_NOT_EXIST("transaction.validation.wallet.doesNotExist"),
    TRANSACTION_VALIDATION_WALLET_DOES_NOT_MATCH_ROUTE("transaction.validation.wallet.doesNotMatchRoute"),

    USER_VALIDATION_EMAIL_CANNOT_BE_EMPTY("user.validation.email.cannotBeEmpty"),
    USER_VALIDATION_EMAIL_CANNOT_BE_LONGER_THEN_CHARACTERS("user.validation.email.cannotBeLongerThenCharacters"),
    USER_VALIDATION_EMAIL_CANNOT_BE_NULL("user.validation.email.cannotBeNull"),
    USER_VALIDATION_EMAIL_IS_ALREADY_USED("user.validation.email.isAlreadyUsed"),
    USER_VALIDATION_EMAIL_IS_INVALID("user.validation.email.isInvalid"),
    USER_VALIDATION_ID_DOES_NOT_MATCH_ROUTE("user.validation.id.doesNotMatchRoute"),
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

    WALLET_BANK_ACCOUNT_VALIDATION_CREDIT_LIMIT_CANNOT_BE_NEGATIVE("wallet.bankAccount.validation.creditLimit.cannotBeNegative"),
    WALLET_BANK_ACCOUNT_VALIDATION_CREDIT_LIMIT_CANNOT_BE_NULL("wallet.bankAccount.validation.creditLimit.cannotBeNull"),
    WALLET_BANK_ACCOUNT_VALIDATION_INTEREST_RATE_CANNOT_BE_NEGATIVE("wallet.bankAccount.validation.interestRate.cannotBeNegative"),
    WALLET_BANK_ACCOUNT_VALIDATION_INTEREST_RATE_CANNOT_BE_NULL("wallet.bankAccount.validation.interestRate.cannotBeNull"),
    WALLET_CASH_WALLET_VALIDATION_BALANCE_CANNOT_BE_NEGATIVE("wallet.cashWallet.validation.balance.cannotBeNegative"),
    WALLET_CREDIT_CARD_VALIDATION_BILLING_CYCLE_DATE_CANNOT_BE_NULL("wallet.creditCard.validation.billingCycleDate.cannotBeNull"),
    WALLET_CREDIT_CARD_VALIDATION_BILLING_CYCLE_DATE_IS_INVALID("wallet.creditCard.validation.billingCycleDate.isInvalid"),
    WALLET_CREDIT_CARD_VALIDATION_CREDIT_LIMIT_CANNOT_BE_NEGATIVE("wallet.creditCard.validation.creditLimit.cannotBeNegative"),
    WALLET_CREDIT_CARD_VALIDATION_CREDIT_LIMIT_CANNOT_BE_NULL("wallet.creditCard.validation.creditLimit.cannotBeNull"),
    WALLET_CREDIT_CARD_VALIDATION_DUE_DATE_CANNOT_BE_NULL("wallet.creditCard.validation.dueDate.cannotBeNull"),
    WALLET_CREDIT_CARD_VALIDATION_DUE_DATE_IS_INVALID("wallet.creditCard.validation.dueDate.isInvalid"),
    WALLET_CRYPTO_WALLET_VALIDATION_BALANCE_CANNOT_BE_NEGATIVE("wallet.cryptoWallet.validation.balance.cannotBeNegative"),
    WALLET_VALIDATION_BALANCE_CANNOT_BE_NULL("wallet.validation.balance.cannotBeNull"),
    WALLET_VALIDATION_CURRENCY_CANNOT_BE_NULL("wallet.validation.currency.cannotBeNull"),
    WALLET_VALIDATION_CURRENCY_DOES_NOT_EXIST("wallet.validation.currency.doesNotExist"),
    WALLET_VALIDATION_ID_DOES_NOT_MATCH_ROUTE("wallet.validation.id.doesNotMatchRoute"),
    WALLET_VALIDATION_NAME_CANNOT_BE_EMPTY("wallet.validation.name.cannotBeEmpty"),
    WALLET_VALIDATION_NAME_CANNOT_BE_LESS_THEN_CHARACTERS("wallet.validation.name.cannotBeLessThenCharacters"),
    WALLET_VALIDATION_NAME_CANNOT_BE_LONGER_THEN_CHARACTERS("wallet.validation.name.cannotBeLongerThenCharacters"),
    WALLET_VALIDATION_NAME_CANNOT_BE_NULL("wallet.validation.name.cannotBeNull"),
    WALLET_VALIDATION_NAME_IS_ALREADY_USED_BY_USER("wallet.validation.name.isAlreadyUsedByUser"),
    WALLET_VALIDATION_USER_ID_CANNOT_BE_NULL("wallet.validation.userId.cannotBeNull"),
    WALLET_VALIDATION_USER_ID_DOES_NOT_EXIST("wallet.validation.userId.doesNotExist"),
    WALLET_VALIDATION_USER_ID_DOES_NOT_MATCH_ROUTE("wallet.validation.userId.doesNotMatchRoute"),
    WALLET_VALIDATION_USER_ID_UNAUTHORIZED("wallet.validation.userId.unauthorized"),
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
