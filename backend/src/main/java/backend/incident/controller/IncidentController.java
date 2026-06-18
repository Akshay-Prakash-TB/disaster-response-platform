package backend.incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.incident.entity.Incident;
import backend.incident.service.IncidentService;

@RestController
@RequestMapping("/incident")
@CrossOrigin(origins = "http://localhost:5173")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @PostMapping("/report")
    public Incident reportIncident(@RequestBody Incident incident) {
        return incidentService.createIncident(incident);
    }

    @GetMapping("/all")
    public List<Incident> getAll() {
        return incidentService.getAllIncidents();
    }

    @PutMapping("/{id}/status")
    public Incident updateStatus(@PathVariable Long id, @RequestParam String status) {
        return incidentService.updateStatus(id, status);
    }
}