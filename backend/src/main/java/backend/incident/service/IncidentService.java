package backend.incident.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import backend.ai.dto.DuplicateResponse;
import backend.ai.service.AIService;
import backend.auth.entity.User;
import backend.auth.repository.UserRepository;
import backend.auth.service.UserService;
import backend.common.service.FileStorageService;
import backend.common.util.DistanceUtil;
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

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AIService aiService;

    private static final double DUPLICATE_RADIUS_METERS = 500;

    private static final int DUPLICATE_TIME_MINUTES = 60;

    public Incident createIncident(
            Incident incident,
            MultipartFile image,
            String token) {

        User user =
                userService
                        .getUserFromToken(
                                token);

        incident.setCitizenId(
                user.getId());

        incident.setStatus(
                "OPEN");

        incident.setReportedAt(
                LocalDateTime.now());

        incident.setPossibleDuplicate(false);

        incident.setDuplicateIncidentId(null);

        incident.setDuplicateScore(null);

        if (image != null && !image.isEmpty()) {

            String imagePath =
                    fileStorageService
                            .saveFile(image);

            incident.setImagePath(
                    imagePath);
        }

        Incident savedIncident =
                incidentRepository
                        .save(incident);

        if (savedIncident.getImagePath() != null) {

            aiService.analyzeIncidentImage(
                    savedIncident.getId(),
                    savedIncident.getImagePath());

        }

        List<Incident> nearbyIncidents =
                getNearbyIncidents(
                        savedIncident);

        if (!nearbyIncidents.isEmpty()) {

            DuplicateResponse response =
                    aiService.checkDuplicate(
                            savedIncident,
                            nearbyIncidents);

            if (response != null &&
                    Boolean.TRUE.equals(
                            response.getDuplicateFound())) {

                savedIncident.setPossibleDuplicate(
                        true);

                savedIncident.setDuplicateIncidentId(
                        response.getBestMatchIncidentId());

                savedIncident.setDuplicateScore(
                        response.getBestSimilarityScore());

                incidentRepository.save(
                        savedIncident);

            }

        }

        return savedIncident;
    }

    private List<Incident> getNearbyIncidents(
            Incident newIncident) {

        LocalDateTime cutoff =
                LocalDateTime.now()
                        .minusMinutes(
                                DUPLICATE_TIME_MINUTES);

        List<Incident> recentIncidents =
                incidentRepository
                        .findByReportedAtAfter(
                                cutoff);

        return recentIncidents

                .stream()

                .filter(existing ->

                        !existing.getId().equals(
                                newIncident.getId()))

                .filter(existing ->

                        DistanceUtil.calculateDistance(

                                newIncident.getLatitude(),

                                newIncident.getLongitude(),

                                existing.getLatitude(),

                                existing.getLongitude()

                        ) <= DUPLICATE_RADIUS_METERS)

                .collect(Collectors.toList());

    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident updateStatus(Long id, String status) {

        Incident incident =
                incidentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Incident not found"));

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

    public List<Incident> getMyIncidents(
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

        for (Incident incident : incidents) {

            IncidentAdminDTO dto =
                    new IncidentAdminDTO();

            dto.setId(
                    incident.getId());

            dto.setTitle(
                    incident.getTitle());

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

            dto.setPossibleDuplicate(
                        incident.getPossibleDuplicate());

                dto.setDuplicateIncidentId(
                        incident.getDuplicateIncidentId());

                dto.setDuplicateScore(
                        incident.getDuplicateScore());

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