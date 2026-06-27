package backend.incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.incident.dto.IncidentAdminDTO;
import backend.incident.entity.Incident;
import backend.incident.service.IncidentService;

@RestController
@RequestMapping("/incident")
@CrossOrigin(origins = "http://localhost:5173")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @PostMapping("/report")
    public Incident reportIncident(

            @RequestPart("incident")
            Incident incident,

            @RequestPart(value = "image", required = false)
            MultipartFile image,

            @RequestHeader("Authorization")
            String authHeader) {

        String token =
                authHeader.replace(
                        "Bearer ",
                        "");

        return incidentService.createIncident(
                incident,
                image,
                token);
    }

    @GetMapping("/all")
    public List<Incident> getAll() {
        return incidentService.getAllIncidents();
    }

    @PutMapping("/{id}/status")
    public Incident updateStatus(@PathVariable Long id, @RequestParam String status) {
        return incidentService.updateStatus(id, status);
    }

    @GetMapping("/search")
    public List<Incident> searchIncidents(@RequestParam String keyword) {
        return incidentService.searchIncidents(keyword);
    }

    @GetMapping("/filter")
    public List<Incident> filterByStatus(@RequestParam String status) {
        return incidentService.filterByStatus(status);
    }

    @GetMapping("/citizen/{citizenId}")
    public List<Incident>
    getCitizenIncidents(@PathVariable Long citizenId) {
        return incidentService.getCitizenIncidents(citizenId);
    }

    @GetMapping("/my-incidents")
    public List<Incident>
    getMyIncidents(
        @RequestHeader("Authorization")
        String authHeader) {

        String token =
                authHeader
                        .replace(
                                "Bearer ",
                                "");

        return incidentService
                .getMyIncidents(
                        token);
    }

    @GetMapping("/admin/all")
    public List<IncidentAdminDTO>
    getAllForAdmin() {

        return incidentService
                .getAllIncidentsForAdmin();
    }
}