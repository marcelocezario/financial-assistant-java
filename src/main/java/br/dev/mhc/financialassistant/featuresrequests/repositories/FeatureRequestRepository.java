package br.dev.mhc.financialassistant.featuresrequests.repositories;

import br.dev.mhc.financialassistant.featuresrequests.entities.FeatureRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureRequestRepository extends JpaRepository<FeatureRequest, Long> {
    List<FeatureRequest> findByActiveTrueAndDevelopedFalseAndApprovedTrueOrderByRatingDesc();
}
