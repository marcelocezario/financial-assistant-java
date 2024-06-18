package br.dev.mhc.financialassistant.featuresrequests.controllers;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;
import br.dev.mhc.financialassistant.featuresrequests.services.interfaces.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RouteConstants.FEATURE_REQUESTS)
public class FeatureRequestController {

    private final ICreateFeatureRequestService createFeatureRequestService;
    private final IUpdateFeatureRequestService updateFeatureRequestService;
    private final IApproveFeatureRequestService approveFeatureRequestService;
    private final IRateFeatureRequestService rateFeatureRequestService;
    private final IFindFeatureRequestBacklogService findFeatureRequestBacklogService;

    public FeatureRequestController(ICreateFeatureRequestService createFeatureRequestService, IUpdateFeatureRequestService updateFeatureRequestService, IApproveFeatureRequestService approveFeatureRequestService, IRateFeatureRequestService rateFeatureRequestService, IFindFeatureRequestBacklogService findFeatureRequestBacklogService) {
        this.createFeatureRequestService = createFeatureRequestService;
        this.updateFeatureRequestService = updateFeatureRequestService;
        this.approveFeatureRequestService = approveFeatureRequestService;
        this.rateFeatureRequestService = rateFeatureRequestService;
        this.findFeatureRequestBacklogService = findFeatureRequestBacklogService;
    }

    @PostMapping
    ResponseEntity<FeatureRequestDTO> create(@RequestBody @Valid FeatureRequestDTO featureRequestDTO) {
        featureRequestDTO = createFeatureRequestService.create(featureRequestDTO);
        return ResponseEntity.created(URIUtils.buildUri(featureRequestDTO.getId())).body(featureRequestDTO);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<FeatureRequestDTO> update(@PathVariable Long id, @RequestBody @Valid FeatureRequestDTO featureRequestDTO) {
        featureRequestDTO.setId(id);
        featureRequestDTO = updateFeatureRequestService.update(featureRequestDTO);
        return ResponseEntity.ok(featureRequestDTO);
    }

    @GetMapping(value = "/backlog")
    ResponseEntity<List<FeatureRequestDTO>> getBacklog() {
        return ResponseEntity.ok(findFeatureRequestBacklogService.find());
    }

    @PatchMapping(value = "/{id}/approve")
    ResponseEntity<FeatureRequestDTO> approveFeatureRequest(@PathVariable Long id) {
        var featureRequest = approveFeatureRequestService.approve(id);
        return ResponseEntity.ok(featureRequest);
    }

    @PostMapping(value = "/{id}/rate")
    ResponseEntity<Void> rateFeatureRequest(@PathVariable Long id, @RequestParam boolean positive) {
        rateFeatureRequestService.rate(id, positive);
        return ResponseEntity.noContent().build();
    }

}
