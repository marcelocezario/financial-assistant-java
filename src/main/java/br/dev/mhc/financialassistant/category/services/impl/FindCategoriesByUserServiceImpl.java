package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.mappers.CategoryMapper;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoriesByUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class FindCategoriesByUserServiceImpl implements IFindCategoriesByUserService {

    private final CategoryRepository repository;

    public FindCategoriesByUserServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> find(UUID userId, boolean onlyActive) {
        requireNonNull(userId);
        List<Category> categories;
        if (onlyActive) {
            categories = repository.findByUserIdAndParentCategoryIsNullAndActiveTrue(userId);
        } else {
            categories = repository.findByUserIdAndParentCategoryIsNull(userId);
        }
        return categories.stream()
                .sorted(Comparator.comparing(Category::getName))
                .map(CategoryMapper::toDto)
                .toList();
    }
}
