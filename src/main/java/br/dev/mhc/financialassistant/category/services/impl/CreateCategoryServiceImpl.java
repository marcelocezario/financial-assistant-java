package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.category.services.interfaces.ICategoryValidatorService;
import br.dev.mhc.financialassistant.category.services.interfaces.ICreateCategoryService;
import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.category.mappers.CategoryMapper.toDto;
import static br.dev.mhc.financialassistant.category.mappers.CategoryMapper.toEntity;
import static java.util.Objects.requireNonNull;

@Service
public class CreateCategoryServiceImpl implements ICreateCategoryService {

    private final CategoryRepository repository;
    private final ICategoryValidatorService validatorService;

    public CreateCategoryServiceImpl(CategoryRepository repository, ICategoryValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        requireNonNull(categoryDTO);
        validatorService.validate(categoryDTO).isValidOrThrow(AppValidationException::new);
        categoryDTO.setId(null);
        categoryDTO.setActive(true);
        var category = toEntity(categoryDTO);
        category = repository.save(category);
        return toDto(category);
    }
}
