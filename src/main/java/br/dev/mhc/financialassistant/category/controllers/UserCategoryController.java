package br.dev.mhc.financialassistant.category.controllers;

import br.dev.mhc.financialassistant.category.dtos.CategoryDTO;
import br.dev.mhc.financialassistant.category.services.interfaces.*;
import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = RouteConstants.USERS_ROUTE + "/{userId}" + RouteConstants.CATEGORIES_ROUTE)
public class UserCategoryController {

    private final ICreateCategoryService createCategoryService;
    private final IUpdateCategoryService updateCategoryService;
    private final IDeleteCategoryService deleteCategoryService;
    private final IFindCategoriesByUserService findCategoriesByUserService;
    private final IFindCategoryByIdAndUserIdService findCategoryByIdAndUserIdService;

    public UserCategoryController(ICreateCategoryService createCategoryService, IUpdateCategoryService updateCategoryService, IDeleteCategoryService deleteCategoryService, IFindCategoriesByUserService findCategoriesByUserService, IFindCategoryByIdAndUserIdService findCategoryByIdAndUserIdService) {
        this.createCategoryService = createCategoryService;
        this.updateCategoryService = updateCategoryService;
        this.deleteCategoryService = deleteCategoryService;
        this.findCategoriesByUserService = findCategoriesByUserService;
        this.findCategoryByIdAndUserIdService = findCategoryByIdAndUserIdService;
    }

    @PostMapping
    ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO categoryDTO) {
        categoryDTO = createCategoryService.create(categoryDTO);
        return ResponseEntity.created(URIUtils.buildUri(categoryDTO.getId())).body(categoryDTO);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<CategoryDTO> update(@RequestBody @Valid CategoryDTO categoryDTO) {
        categoryDTO = updateCategoryService.update(categoryDTO);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID userId, @PathVariable UUID id) {
        deleteCategoryService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    ResponseEntity<List<CategoryDTO>> getByUser(@PathVariable UUID userId, @RequestParam(value = "onlyActive", defaultValue = "true") boolean onlyActive) {
        return ResponseEntity.ok(findCategoriesByUserService.find(userId, onlyActive));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<CategoryDTO> getById(@PathVariable UUID userId, @PathVariable UUID id) {
        return ResponseEntity.ok(findCategoryByIdAndUserIdService.find(id, userId));
    }
}
