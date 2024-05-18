package br.dev.mhc.financialassistant.category.services.interfaces;

import java.util.UUID;

public interface IDeleteCategoryService {

    void delete(UUID id, UUID userId);
}
