package br.dev.mhc.financialassistant.featuresrequests.services.interfaces;

import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;

public interface IApproveFeatureRequestService {

    FeatureRequestDTO approve(Long id);
}
