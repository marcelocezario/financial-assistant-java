package br.dev.mhc.financialassistant.featuresrequests.services.interfaces;

import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;

public interface IUpdateFeatureRequestService {

    FeatureRequestDTO update(FeatureRequestDTO featureRequestDTO);
}
