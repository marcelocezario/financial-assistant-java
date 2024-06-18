package br.dev.mhc.financialassistant.featuresrequests.services.interfaces;

import br.dev.mhc.financialassistant.featuresrequests.dtos.FeatureRequestDTO;

import java.util.List;

public interface IFindFeatureRequestBacklogService {

    List<FeatureRequestDTO> find();
}
