package backend.assignment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.assignment.dto.ActiveMissionDTO;
import backend.assignment.dto.RescueMissionDTO;
import backend.assignment.entity.Assignment;
import backend.assignment.repository.AssignmentRepository;
import backend.auth.entity.User;
import backend.auth.repository.UserRepository;
import backend.incident.entity.Incident;
import backend.incident.repository.IncidentRepository;
import backend.notification.service.NotificationService;
import backend.resource.entity.Resource;
import backend.resource.repository.ResourceRepository;
import backend.tracking.service.TrackingService;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public Assignment createAssignment(
            Long incidentId,
            Long resourceId) {

        Assignment assignment =
                new Assignment();

        assignment.setIncidentId(
                incidentId);

        assignment.setResourceId(
                resourceId);

        assignment.setStatus(
                "ASSIGNED");

        assignment.setAssignedAt(
                LocalDateTime.now());

        return assignmentRepository
                .save(assignment);
    }

    @Transactional
    public Assignment acceptMission(
        Long assignmentId) {

        Assignment assignment =
                assignmentRepository
                        .findById(assignmentId)
                        .orElseThrow();

        assignment.setStatus(
                "IN_PROGRESS");

        assignment.setAcceptedAt(
                LocalDateTime.now());

        trackingService.initializeTracking(
        assignment.getResourceId(),
        assignment.getIncidentId());

        Incident incident =
                incidentRepository
                        .findById(
                                assignment.getIncidentId())
                        .orElseThrow();

        incident.setStatus(
                "IN_PROGRESS");

        incidentRepository.save(
                incident);

        notificationService.createNotification(
                incident.getCitizenId(),
                "Mission Started",
                "Rescue team has started responding."
        );

        List<User> admins =
                userRepository.findByRole("ADMIN");

        for(User admin : admins) {

        notificationService.createNotification(
                admin.getId(),
                "Mission Accepted",
                "A rescue team has accepted a mission."
        );
        }

        return assignmentRepository
                .save(assignment);
    }

    @Transactional
    public Assignment completeMission(
        Long assignmentId) {

        Assignment assignment =
                assignmentRepository
                        .findById(assignmentId)
                        .orElseThrow();

        assignment.setStatus(
                "COMPLETED");

        assignment.setCompletedAt(
                LocalDateTime.now());

        trackingService.stopTracking(
        assignment.getResourceId());

        Incident incident =
                incidentRepository
                        .findById(
                                assignment.getIncidentId())
                        .orElseThrow();

        incident.setStatus(
                "RESOLVED");

        incidentRepository.save(
                incident);

        Resource resource =
                resourceRepository
                        .findById(
                                assignment.getResourceId())
                        .orElseThrow();

        resource.setStatus(
                "AVAILABLE");

        resource.setIncidentId(
                null);

        resourceRepository.save(
                resource);

        notificationService.createNotification(
                incident.getCitizenId(),
                "Incident Resolved",
                "The rescue team has completed the mission and your incident has been resolved."
        );

        List<User> admins =
        userRepository.findByRole("ADMIN");

        for(User admin : admins) {

        notificationService.createNotification(
                admin.getId(),
                "Mission Completed",
                "A rescue team has completed a mission."
        );
        }

        return assignmentRepository
                .save(assignment);
    }

    public List<Assignment> getAssignedMissions() {
        return assignmentRepository
                .findByStatusIn(
                        List.of(
                                "ASSIGNED",
                                "IN_PROGRESS"));
    }

    public Assignment getByIncident(
        Long incidentId) {

        return assignmentRepository
                .findFirstByIncidentId(
                        incidentId)
                .orElse(null);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAllByOrderByAssignedAtDesc();
    }

    public List<RescueMissionDTO>
getRescueMissions(
        Long userId) {

        List<Resource> resources =
                resourceRepository
                        .findByAssignedUserId(
                                userId);

        List<Long> resourceIds =
                resources.stream()
                        .map(Resource::getId)
                        .toList();

        List<Assignment> assignments =
                assignmentRepository
                        .findByResourceIdIn(
                                resourceIds);

        List<RescueMissionDTO> missions =
                new ArrayList<>();

        for (Assignment assignment : assignments) {

                Incident incident =
                        incidentRepository
                                .findById(
                                        assignment.getIncidentId())
                                .orElseThrow();

                RescueMissionDTO dto =
                        new RescueMissionDTO();

                dto.setAssignmentId(
                        assignment.getId());

                dto.setIncidentId(
                        incident.getId());

                dto.setTitle(
                        incident.getTitle());

                dto.setDescription(
                        incident.getDescription());

                dto.setSeverity(
                        incident.getSeverity());

                dto.setLatitude(
                        incident.getLatitude());

                dto.setLongitude(
                        incident.getLongitude());

                dto.setCitizenId(
                        incident.getCitizenId());

                User citizen =
                        userRepository
                                .findById(
                                        incident.getCitizenId())
                                .orElse(null);

                if(citizen != null) {

                dto.setCitizenName(
                        citizen.getName());

                dto.setCitizenEmail(
                        citizen.getEmail());
                }

                dto.setAssignmentStatus(
                        assignment.getStatus());

                missions.add(dto);
        }

        return missions;
        }

        public List<ActiveMissionDTO>
        getActiveMissions() {

        List<Assignment> assignments =
                assignmentRepository
                        .findByStatusIn(
                                List.of(
                                        "ASSIGNED",
                                        "IN_PROGRESS"));

        List<ActiveMissionDTO> result =
                new ArrayList<>();

        for (Assignment assignment : assignments) {

                Incident incident =
                        incidentRepository
                                .findById(
                                        assignment.getIncidentId())
                                .orElseThrow();

                Resource resource =
                        resourceRepository
                                .findById(
                                        assignment.getResourceId())
                                .orElseThrow();

                ActiveMissionDTO dto =
                        new ActiveMissionDTO();

                dto.setAssignmentId(
                        assignment.getId());

                dto.setIncidentTitle(
                        incident.getTitle());

                dto.setResourceName(
                        resource.getName());

                dto.setStatus(
                        assignment.getStatus());

                if (resource.getAssignedUserId() != null) {

                User user =
                        userRepository
                                .findById(
                                        resource.getAssignedUserId())
                                .orElseThrow();

                dto.setRescueTeam(
                        user.getName());
                }

                result.add(dto);
        }

        return result;
        }
}