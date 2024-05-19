package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.category.services.interfaces.ICategoryValidatorService;
import br.dev.mhc.financialassistant.category.services.interfaces.IUpdateCategoryService;
import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.category.mappers.CategoryMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class UpdateCategoryServiceImpl implements IUpdateCategoryService {

    private final CategoryRepository repository;
    private final ICategoryValidatorService validatorService;

    public UpdateCategoryServiceImpl(CategoryRepository repository, ICategoryValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        requireNonNull(categoryDTO);
        requireNonNull(categoryDTO.getId());
        Category categoryEntity = repository.getReferenceById(categoryDTO.getId());
        try {
            updateData(categoryEntity, categoryDTO);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(categoryDTO.getId(), Category.class);
        }
        validatorService.validate(categoryDTO).isValidOrThrow(AppValidationException::new);
        categoryDTO = toDto(repository.save(categoryEntity));
        return categoryDTO;
    }

    private void updateData(Category categoryEntity, CategoryDTO categoryDTO) {
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setIcon(categoryDTO.getIcon());
        categoryEntity.setColor(categoryDTO.getColor());
    }
}
