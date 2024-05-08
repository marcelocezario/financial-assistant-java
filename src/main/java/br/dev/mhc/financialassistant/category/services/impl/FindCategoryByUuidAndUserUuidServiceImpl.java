package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoryByUuidAndUserUuidService;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.dev.mhc.financialassistant.category.mappers.CategoryMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class FindCategoryByUuidAndUserUuidServiceImpl implements IFindCategoryByUuidAndUserUuidService {

    private final CategoryRepository repository;

    public FindCategoryByUuidAndUserUuidServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDTO find(UUID uuid, UUID userUuid) {
        requireNonNull(uuid);
        requireNonNull(userUuid);
        var category = repository.findByUuidAndUserUuid(uuid, userUuid).orElseThrow(() -> new ResourceNotFoundException(uuid, Category.class));
        return toDto(category);
    }
}
