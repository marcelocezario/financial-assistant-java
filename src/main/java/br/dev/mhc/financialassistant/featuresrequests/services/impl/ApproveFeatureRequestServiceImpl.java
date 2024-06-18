package br.dev.mhc.financialassistant.featuresrequests.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;
import br.dev.mhc.financialassistant.featuresrequests.entities.FeatureRequest;
import br.dev.mhc.financialassistant.featuresrequests.repositories.FeatureRequestRepository;
import br.dev.mhc.financialassistant.featuresrequests.services.interfaces.IApproveFeatureRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.featuresrequests.mappers.FeatureRequestMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class ApproveFeatureRequestServiceImpl implements IApproveFeatureRequestService {

    private final FeatureRequestRepository repository;

    public ApproveFeatureRequestServiceImpl(FeatureRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public FeatureRequestDTO approve(Long id) {
        requireNonNull(id);
        var entity = repository.getReferenceById(id);
        try {
            entity.setApproved(true);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id, FeatureRequest.class);
        }
        entity = repository.save(entity);
        return toDto(entity);
    }
}
