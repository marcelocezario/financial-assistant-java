package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.mappers.CategoryMapper;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoriesByUserUuidService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class FindCategoriesByUserUuidServiceImpl implements IFindCategoriesByUserUuidService {

    private final CategoryRepository repository;

    public FindCategoriesByUserUuidServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoryDTO> find(UUID userUuid, boolean onlyActive) {
        requireNonNull(userUuid);
        List<Category> categories;
        if (onlyActive) {
            categories = repository.findByUserUuidAndActiveTrue(userUuid);
        } else {
            categories = repository.findByUserUuid(userUuid);
        }
        return categories.stream()
                .sorted(Comparator.comparing(Category::getName))
                .map(CategoryMapper::toDto)
                .toList();
    }
}
