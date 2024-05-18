package br.dev.mhc.financialassistant.category.services.impl;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.category.services.interfaces.IDeleteCategoryService;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class DeleteCategoryServiceImpl implements IDeleteCategoryService {

    private final CategoryRepository repository;

    public DeleteCategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(UUID id, UUID userId) {
        requireNonNull(id);
        requireNonNull(userId);
        var category = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(id, Category.class));
        repository.delete(category);
    }
}
