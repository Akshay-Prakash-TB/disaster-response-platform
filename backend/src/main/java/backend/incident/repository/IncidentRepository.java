package backend.incident.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.incident.entity.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
}