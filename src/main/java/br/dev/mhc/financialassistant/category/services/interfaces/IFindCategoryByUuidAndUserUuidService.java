package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;

import java.util.UUID;

public interface IFindCategoryByUuidAndUserUuidService {

    CategoryDTO find(UUID uuid, UUID userUuid);
}
