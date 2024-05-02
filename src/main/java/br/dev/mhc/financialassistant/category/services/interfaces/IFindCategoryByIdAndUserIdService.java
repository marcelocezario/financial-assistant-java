package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

public interface IFindCategoryByIdAndUserIdService {

    CategoryDTO find(Long id, Long userId);
}
