package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

import java.util.UUID;

public interface IFindCategoryByIdAndUserIdService {

    CategoryDTO find(UUID id, UUID userId);
}
