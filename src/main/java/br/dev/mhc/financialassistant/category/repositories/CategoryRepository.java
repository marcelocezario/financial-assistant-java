package br.dev.mhc.financialassistant.category.repositories;

import br.dev.mhc.financialassistant.category.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByUserIdAndParentCategoryIsNull(UUID userId);

    List<Category> findByUserIdAndParentCategoryIsNullAndActiveTrue(UUID userId);

    Optional<Category> findByNameAndUserId(String categoryName, UUID userId);

    Optional<Category> findByIdAndUserId(UUID id, UUID userId);
}
