package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoryByNameAndUserService;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.dev.mhc.financialassistant.category.mappers.CategoryMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindCategoryByNameAndUserServiceImpl implements IFindCategoryByNameAndUserService {

    private final CategoryRepository repository;

    public FindCategoryByNameAndUserServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDTO find(String categoryName, UUID userId) {
        requireNonNull(categoryName);
        requireNonNull(userId);
        var category = repository.findByNameAndUserId(categoryName, userId)
                .orElseThrow(() -> new ResourceNotFoundException(categoryName, Category.class));
        return toDto(category);
    }
}
