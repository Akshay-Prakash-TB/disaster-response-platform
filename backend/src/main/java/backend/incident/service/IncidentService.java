package backend.incident.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.auth.entity.User;
import backend.auth.repository.UserRepository;
import backend.auth.service.UserService;
import backend.incident.dto.IncidentAdminDTO;
import backend.incident.entity.Incident;
import backend.incident.repository.IncidentRepository;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Incident createIncident(
    Incident incident,
    String token) {

    User user =
            userService
                    .getUserFromToken(
                            token);

    incident.setCitizenId(
            user.getId());

    incident.setStatus(
            "OPEN");

    return incidentRepository
            .save(incident);

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

    public List<Incident>
getMyIncidents(
        String token) {

        User user =
                userService
                        .getUserFromToken(
                                token);

        return incidentRepository
                .findByCitizenId(
                        user.getId());
    }

    public List<IncidentAdminDTO> getAllIncidentsForAdmin() {

        List<Incident> incidents =
                incidentRepository.findAll();

        List<IncidentAdminDTO> result =
                new ArrayList<>();

        for(Incident incident : incidents) {

                IncidentAdminDTO dto =
                        new IncidentAdminDTO();

                dto.setId(incident.getId());
                dto.setTitle(incident.getTitle());
                dto.setDescription(
                        incident.getDescription());
                dto.setSeverity(
                        incident.getSeverity());
                dto.setStatus(
                        incident.getStatus());
                dto.setIncidentType(
                        incident.getIncidentType());

                dto.setCitizenId(
                        incident.getCitizenId());

                userRepository
                .findById(
                        incident.getCitizenId())
                .ifPresent(user -> {

                        dto.setCitizenName(
                                user.getName());

                        dto.setCitizenEmail(
                                user.getEmail());
                });

                result.add(dto);
        }

        return result;
        }
}