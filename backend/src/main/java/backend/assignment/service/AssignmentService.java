package backend.assignment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.assignment.entity.Assignment;
import backend.assignment.repository.AssignmentRepository;
import backend.incident.entity.Incident;
import backend.incident.repository.IncidentRepository;
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
}