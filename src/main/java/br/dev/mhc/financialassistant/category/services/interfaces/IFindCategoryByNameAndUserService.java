package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

import java.util.UUID;

public interface IFindCategoryByNameAndUserService {

    CategoryDTO find(String categoryName, UUID userId);
}
