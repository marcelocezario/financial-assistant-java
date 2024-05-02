package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

public interface IUpdateCategoryService {

    CategoryDTO update(CategoryDTO categoryDTO);
}
