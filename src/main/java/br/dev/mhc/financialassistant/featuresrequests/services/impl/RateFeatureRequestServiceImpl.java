package br.dev.mhc.financialassistant.featuresrequests.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;
import br.dev.mhc.financialassistant.featuresrequests.entities.FeatureRequest;
import br.dev.mhc.financialassistant.featuresrequests.repositories.FeatureRequestRepository;
import br.dev.mhc.financialassistant.featuresrequests.services.interfaces.IRateFeatureRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.dev.mhc.financialassistant.featuresrequests.mappers.FeatureRequestMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class RateFeatureRequestServiceImpl implements IRateFeatureRequestService {

    private final FeatureRequestRepository repository;

    public RateFeatureRequestServiceImpl(FeatureRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public FeatureRequestDTO rate(Long id, boolean positive) {
        requireNonNull(id);
        var entity = repository.getReferenceById(id);
        try {
            entity.addRating(positive);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id, FeatureRequest.class);
        }
        entity = repository.save(entity);
        return toDto(entity);
    }
}
