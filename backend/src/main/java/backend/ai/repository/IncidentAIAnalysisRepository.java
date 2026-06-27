package backend.ai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.ai.entity.IncidentAIAnalysis;

public interface IncidentAIAnalysisRepository
        extends JpaRepository<IncidentAIAnalysis, Long> {

    Optional<IncidentAIAnalysis>
    findByIncidentId(Long incidentId);

}