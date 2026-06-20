package backend.incident.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.incident.entity.Incident;
import backend.incident.repository.IncidentRepository;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public Incident createIncident(Incident incident) {
        incident.setStatus("OPEN");
        return incidentRepository.save(incident);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident updateStatus(Long id, String status) {

        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Incident not found"));

        incident.setStatus(status);

        return incidentRepository.save(incident);
    }

    public List<Incident> searchIncidents(String keyword) {
        return incidentRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Incident> filterByStatus(String status) {
        return incidentRepository.findByStatus(status);
    }

    public List<Incident> getCitizenIncidents(Long citizenId) {
        return incidentRepository.findByCitizenId(citizenId);
    }
}