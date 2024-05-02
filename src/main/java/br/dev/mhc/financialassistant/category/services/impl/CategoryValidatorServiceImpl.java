package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.annotations.CategoryDTOValidator;
import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.services.interfaces.ICategoryValidatorService;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoryByIdAndUserIdService;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoryByNameAndUserService;
import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;
import br.dev.mhc.financialassistant.common.logs.LogHelper;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.user.services.interfaces.IFindUserByIdService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.dev.mhc.financialassistant.common.translation.TranslationKey.*;
import static java.util.Objects.*;

@Service
public class CategoryValidatorServiceImpl implements ICategoryValidatorService, ConstraintValidator<CategoryDTOValidator, CategoryDTO> {

    private final static LogHelper LOG = new LogHelper(CategoryValidatorServiceImpl.class);

    private final IFindCategoryByNameAndUserService findCategoryByNameAndUser;
    private final IFindCategoryByIdAndUserIdService findCategoryByIdAndUserIdService;
    private final IFindUserByIdService findUserByIdService;
    private final HttpServletRequest request;

    public CategoryValidatorServiceImpl(IFindCategoryByNameAndUserService findCategoryByNameAndUser, IFindCategoryByIdAndUserIdService findCategoryByIdAndUserIdService, IFindUserByIdService findUserByIdService, HttpServletRequest request) {
        this.findCategoryByNameAndUser = findCategoryByNameAndUser;
        this.findCategoryByIdAndUserIdService = findCategoryByIdAndUserIdService;
        this.findUserByIdService = findUserByIdService;
        this.request = request;
    }

    @Override
    public ValidationResultDTO<CategoryDTO> validate(CategoryDTO categoryDTO) {
        requireNonNull(categoryDTO);

        var validation = new ValidationResultDTO<>(categoryDTO);

        validateName(validation);
        validateIconUrl(validation);
        validateColor(validation);
        validateUserId(validation);

        LOG.debug(validation);

        return validation;
    }

    @Override
    public boolean isValid(CategoryDTO categoryDTO, ConstraintValidatorContext constraintValidatorContext) {
        var uri = request.getRequestURI();
        categoryDTO.setId(URIUtils.findIdAfterPath(uri, RouteConstants.CATEGORIES_ROUTE));
        categoryDTO.setUserId(URIUtils.findIdAfterPath(uri, RouteConstants.USERS_ROUTE));
        var validationResult = validate(categoryDTO);
        validationResult.getErrors().forEach(error -> {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(error.getMessage())
                    .addPropertyNode(error.getFieldName())
                    .addConstraintViolation();
        });
        return validationResult.isValid();
    }

    private void validateUserId(ValidationResultDTO<CategoryDTO> validation) {
        final var FIELD_NAME = "userId";
        var userId = validation.getObject().getUserId();
        if (isNull(userId)) {
            validation.addError(FIELD_NAME, null, CATEGORY_VALIDATION_USER_ID_CANNOT_BE_NULL.translate());
            return;
        }
        try {
            findUserByIdService.find(userId);
        } catch (ResourceNotFoundException e) {
            validation.addError(FIELD_NAME, userId, CATEGORY_VALIDATION_USER_ID_DOES_NOT_EXIST.translate());
        }
        var categoryId = validation.getObject().getId();
        if (nonNull(categoryId)) {
            try {
                findCategoryByIdAndUserIdService.find(categoryId, userId);
            } catch (ResourceNotFoundException e) {
                validation.addError(FIELD_NAME, userId, CATEGORY_VALIDATION_USER_ID_UNAUTHORIZED.translate());
            }
        }
    }

    private void validateColor(ValidationResultDTO<CategoryDTO> validation) {
        final var FIELD_NAME = "color";
        var color = validation.getObject().getColor();
        final var LENGTH = 7;
        if (isNull(color)) {
            return;
        }
        if (color.length() != LENGTH) {
            validation.addError(FIELD_NAME, color, CATEGORY_VALIDATION_COLOR_MUST_HAVE_CHARACTERS.translate(LENGTH));
        }
        if (!validateHexadecimalColor(color)) {
            validation.addError(FIELD_NAME, color, CATEGORY_VALIDATION_COLOR_IS_INVALID.translate());
        }
    }

    private boolean validateHexadecimalColor(String color) {
        Pattern pattern = Pattern.compile("^#?([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$");
        Matcher matcher = pattern.matcher(color);
        return matcher.matches();
    }

    private void validateIconUrl(ValidationResultDTO<CategoryDTO> validation) {
        final var FIELD_NAME = "iconUrl";
        var iconUrl = validation.getObject().getIconUrl();
        final var MAX_LENGTH = 255;
        if (isNull(iconUrl)) {
            return;
        }
        if (iconUrl.isBlank()) {
            return;
        }
        if (iconUrl.length() > MAX_LENGTH) {
            validation.addError(FIELD_NAME, iconUrl, CATEGORY_VALIDATION_ICON_URL_CANNOT_BE_LONGER_THEN_CHARACTERS.translate(MAX_LENGTH));
        }
    }

    private void validateName(ValidationResultDTO<CategoryDTO> validation) {
        final var FIELD_NAME = "name";
        var name = validation.getObject().getName();
        var userId = validation.getObject().getUserId();
        final var MIN_LENGTH = 3;
        final var MAX_LENGTH = 255;
        if (isNull(name)) {
            validation.addError(FIELD_NAME, name, CATEGORY_VALIDATION_NAME_CANNOT_BE_NULL.translate());
            return;
        }
        if (name.isBlank()) {
            validation.addError(FIELD_NAME, name, CATEGORY_VALIDATION_NAME_CANNOT_BE_EMPTY.translate());
        }
        if (name.length() < MIN_LENGTH) {
            validation.addError(FIELD_NAME, name, CATEGORY_VALIDATION_NAME_CANNOT_BE_LESS_THEN_CHARACTERS.translate(MIN_LENGTH));
        }
        if (name.length() > MAX_LENGTH) {
            validation.addError(FIELD_NAME, name, CATEGORY_VALIDATION_NAME_CANNOT_BE_LONGER_THEN_CHARACTERS.translate(MAX_LENGTH));
        }
        if (nonNull(userId)) {
            try {
                var category = findCategoryByNameAndUser.find(name, userId);
                if (!category.getId().equals(validation.getObject().getId())) {
                    validation.addError(FIELD_NAME, name, CATEGORY_VALIDATION_NAME_IS_ALREADY_USED_BY_USER.translate());
                }
            } catch (ResourceNotFoundException e) {
                // is valid category
            }
        }
    }

}
