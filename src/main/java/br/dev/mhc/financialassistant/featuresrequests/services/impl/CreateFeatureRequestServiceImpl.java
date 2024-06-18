package br.dev.mhc.financialassistant.featuresrequests.services.impl;

import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;
import br.dev.mhc.financialassistant.featuresrequests.repositories.FeatureRequestRepository;
import br.dev.mhc.financialassistant.featuresrequests.services.interfaces.ICreateFeatureRequestService;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.featuresrequests.mappers.FeatureRequestMapper.toDto;
import static br.dev.mhc.financialassistant.featuresrequests.mappers.FeatureRequestMapper.toEntity;
import static java.util.Objects.requireNonNull;

@Service
public class CreateFeatureRequestServiceImpl implements ICreateFeatureRequestService {

    private final FeatureRequestRepository repository;

    public CreateFeatureRequestServiceImpl(FeatureRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public FeatureRequestDTO create(FeatureRequestDTO featureRequestDTO) {
        requireNonNull(featureRequestDTO);
        featureRequestDTO.setId(null);
        featureRequestDTO.setRating(0);
        featureRequestDTO.setDeveloped(false);
        featureRequestDTO.setApproved(false);
        featureRequestDTO.setActive(true);
        featureRequestDTO.getTranslations().forEach((key, value) -> value.setActive(true));
        var featureRequest = toEntity(featureRequestDTO);
        featureRequest = repository.save(featureRequest);
        return toDto(featureRequest);
    }
}
