package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

public interface IFindCategoryByNameAndUserService {

    CategoryDTO find(String categoryName, Long userId);
}
