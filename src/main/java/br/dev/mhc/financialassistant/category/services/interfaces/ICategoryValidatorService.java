package br.dev.mhc.financialassistant.category.services.interfaces;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.common.dtos.ValidationResultDTO;

public interface ICategoryValidatorService {

    ValidationResultDTO<CategoryDTO> validate(CategoryDTO categoryDTO);
}
