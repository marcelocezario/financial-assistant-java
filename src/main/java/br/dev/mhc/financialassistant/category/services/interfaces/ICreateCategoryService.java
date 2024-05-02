package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

public interface ICreateCategoryService {

    CategoryDTO create(CategoryDTO categoryDTO);
}
