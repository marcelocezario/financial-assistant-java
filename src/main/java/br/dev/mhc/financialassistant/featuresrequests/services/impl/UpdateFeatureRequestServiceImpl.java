package br.dev.mhc.financialassistant.featuresrequests.services.impl;

import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;
import br.dev.mhc.financialassistant.featuresrequests.entities.FeatureRequest;
import br.dev.mhc.financialassistant.featuresrequests.repositories.FeatureRequestRepository;
import br.dev.mhc.financialassistant.featuresrequests.services.interfaces.IUpdateFeatureRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.featuresrequests.mappers.FeatureRequestMapper.toDto;
import static java.util.Objects.requireNonNull;

@Service
public class UpdateFeatureRequestServiceImpl implements IUpdateFeatureRequestService {

    private final FeatureRequestRepository repository;

    public UpdateFeatureRequestServiceImpl(FeatureRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public FeatureRequestDTO update(FeatureRequestDTO featureRequestDTO) {
        requireNonNull(featureRequestDTO);
        requireNonNull(featureRequestDTO.getId());
        var featureRequestEntity = repository.getReferenceById(featureRequestDTO.getId());
        try {
            updateData(featureRequestEntity, featureRequestDTO);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(featureRequestDTO.getId(), FeatureRequest.class);
        }
        featureRequestEntity = repository.save(featureRequestEntity);
        return toDto(featureRequestEntity);
    }

    private void updateData(FeatureRequest featureRequestEntity, FeatureRequestDTO featureRequestDTO) {
        featureRequestEntity.setDescription(featureRequestDTO.getDescription());
        featureRequestEntity.setDeveloped(featureRequestDTO.isDeveloped());
        featureRequestEntity.setApproved(featureRequestDTO.isApproved());
        featureRequestEntity.setActive(featureRequestDTO.isActive());

        featureRequestEntity.getTranslations().keySet().retainAll(featureRequestDTO.getTranslations().keySet());

        featureRequestDTO.getTranslations().forEach((key, translationDto) -> {
            if (featureRequestEntity.getTranslations().containsKey(key)) {
                var translationEntity = featureRequestEntity.getTranslations().get(key);
                translationEntity.setTitle(translationDto.getTitle());
                translationEntity.setDescription(translationDto.getDescription());
            } else {
                featureRequestEntity.addTranslation(key, translationDto.getTitle(), translationDto.getDescription());
            }
        });

    }

}
