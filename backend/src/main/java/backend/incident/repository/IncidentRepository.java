package backend.incident.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.incident.entity.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByTitleContainingIgnoreCase(String keyword);
    List<Incident> findByStatus(String status);
    List<Incident> findByCitizenId(Long citizenId);
    List<Incident> findByReportedAtAfter(
        LocalDateTime time);
}