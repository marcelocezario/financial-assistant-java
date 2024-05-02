package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoryByIdAndUserIdService;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.category.mappers.CategoryMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindCategoryByIdAndUserIdServiceImpl implements IFindCategoryByIdAndUserIdService {

    private final CategoryRepository repository;

    public FindCategoryByIdAndUserIdServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDTO find(Long id, Long userId) {
        requireNonNull(id);
        requireNonNull(userId);
        var category = repository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException(id, Category.class));
        return toDto(category);
    }
}
