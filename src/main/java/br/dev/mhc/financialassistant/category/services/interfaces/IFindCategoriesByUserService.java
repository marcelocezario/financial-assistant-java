package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

import java.util.List;

public interface IFindCategoriesByUserService {

    List<CategoryDTO> find(Long userId, boolean onlyActive);
}
