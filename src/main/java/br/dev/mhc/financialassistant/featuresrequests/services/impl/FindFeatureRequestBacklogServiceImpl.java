package br.dev.mhc.financialassistant.featuresrequests.services.impl;

import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;
import br.dev.mhc.financialassistant.featuresrequests.mappers.FeatureRequestMapper;
import br.dev.mhc.financialassistant.featuresrequests.repositories.FeatureRequestRepository;
import br.dev.mhc.financialassistant.featuresrequests.services.interfaces.IFindFeatureRequestBacklogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindFeatureRequestBacklogServiceImpl implements IFindFeatureRequestBacklogService {

    private final FeatureRequestRepository repository;

    public FindFeatureRequestBacklogServiceImpl(FeatureRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FeatureRequestDTO> find() {
        return repository.findByActiveTrueAndDevelopedFalseAndApprovedTrueOrderByRatingDesc().stream().map(FeatureRequestMapper::toDto).toList();
    }
}
