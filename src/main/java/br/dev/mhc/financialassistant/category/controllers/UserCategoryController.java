package br.dev.mhc.financialassistant.category.controllers;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.services.interfaces.ICreateCategoryService;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoriesByUserUuidService;
import br.dev.mhc.financialassistant.category.services.interfaces.IFindCategoryByUuidAndUserUuidService;
import br.dev.mhc.financialassistant.category.services.interfaces.IUpdateCategoryService;
import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = RouteConstants.USERS_ROUTE + "/{userUuid}" + RouteConstants.CATEGORIES_ROUTE)
public class UserCategoryController {

    private final ICreateCategoryService createCategoryService;
    private final IUpdateCategoryService updateCategoryService;
    private final IFindCategoriesByUserUuidService findCategoriesByUserService;
    private final IFindCategoryByUuidAndUserUuidService findCategoryByUuidAndUserUuidService;

    @PostMapping
    ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO categoryDTO) {
        categoryDTO = createCategoryService.create(categoryDTO);
        return ResponseEntity.created(URIUtils.buildUri(categoryDTO.getUuid())).body(categoryDTO);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<CategoryDTO> update(@RequestBody @Valid CategoryDTO categoryDTO) {
        categoryDTO = updateCategoryService.update(categoryDTO);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping
    ResponseEntity<List<CategoryDTO>> getByUser(@PathVariable Long userUuid, @RequestParam(value = "onlyActive", defaultValue = "true") boolean onlyActive) {
        return ResponseEntity.ok(findCategoriesByUserService.find(userUuid, onlyActive));
    }

    @GetMapping(value = "/{uuid}")
    ResponseEntity<CategoryDTO> getByUuid(@PathVariable UUID userUuid, @PathVariable UUID uuid) {
        return ResponseEntity.ok(findCategoryByUuidAndUserUuidService.find(uuid, userUuid));
    }
}
