package br.dev.mhc.financialassistant.category.repositories;

import br.dev.mhc.financialassistant.category.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByUserUuid(UUID userUuid);

    List<Category> findByUserUuidAndActiveTrue(UUID userUuid);

    Optional<Category> findByNameAndUserUuid(String categoryName, UUID userUuid);

    Optional<Category> findByUuidAndUserUuid(UUID uuid, UUID userUuid);
}
