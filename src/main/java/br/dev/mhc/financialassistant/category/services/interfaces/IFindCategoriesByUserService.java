package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface IFindCategoriesByUserService {

    List<CategoryDTO> find(UUID userId, boolean onlyActive);
}
