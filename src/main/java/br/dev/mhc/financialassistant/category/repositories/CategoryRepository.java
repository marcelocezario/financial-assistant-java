package br.dev.mhc.financialassistant.category.repositories;

import br.dev.mhc.financialassistant.category.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long userId);

    List<Category> findByUserIdAndActiveTrue(Long userId);

    Optional<Category> findByNameAndUserId(String categoryName, Long userId);

    Optional<Category> findByIdAndUserId(Long id, Long userId);
}
