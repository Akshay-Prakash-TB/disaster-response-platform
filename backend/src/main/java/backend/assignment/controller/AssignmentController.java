package backend.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.assignment.dto.ActiveMissionDTO;
import backend.assignment.dto.RescueMissionDTO;
import backend.assignment.entity.Assignment;
import backend.assignment.service.AssignmentService;

@RestController
@RequestMapping("/assignment")
@CrossOrigin("*")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/assigned")
    public List<Assignment>
    getAssignedMissions() {

        return assignmentService
                .getAssignedMissions();
    }

    @PutMapping("/{id}/accept")
    public Assignment acceptMission(
            @PathVariable Long id) {

        return assignmentService
                .acceptMission(id);
    }

    @PutMapping("/{id}/complete")
    public Assignment completeMission(
            @PathVariable Long id) {

        return assignmentService
                .completeMission(id);
    }

    @GetMapping("/incident/{incidentId}")
    public Assignment getByIncident(@PathVariable Long incidentId) {
        return assignmentService
                .getByIncident(
                        incidentId);
    }

    @GetMapping("/all")
    public List<Assignment>
    getAllAssignments() {
        return assignmentService
                .getAllAssignments();
    }

    @GetMapping("/resource/{resourceId}")
        public List<RescueMissionDTO>
        getResourceAssignments(
                @PathVariable Long resourceId) {

        return assignmentService
                .getRescueMissions(
                        resourceId);
        }

        @GetMapping("/user/{userId}")
        public List<RescueMissionDTO>
        getUserAssignments(
                @PathVariable Long userId) {

        return assignmentService
                .getRescueMissions(
                        userId);
        }

        @GetMapping("/active")
        public List<ActiveMissionDTO>
        getActiveMissions() {

        return assignmentService
                .getActiveMissions();
        }
}