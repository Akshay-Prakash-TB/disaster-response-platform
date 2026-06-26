package backend.relief.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.relief.entity.ReliefRequest;
import backend.relief.service.ReliefRequestService;

@RestController
@RequestMapping("/api/relief")
@CrossOrigin(origins = "http://localhost:5173")
public class ReliefRequestController {

    @Autowired
    private ReliefRequestService service;

    // ---------------- CREATE ----------------
    @PostMapping
    public ResponseEntity<ReliefRequest> create(@RequestBody ReliefRequest request) {
        return ResponseEntity.ok(service.createRequest(request));
    }

    // ---------------- GET ALL ----------------
    @GetMapping
    public ResponseEntity<List<ReliefRequest>> getAll() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    // ---------------- GET BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<ReliefRequest> getById(@PathVariable Long id) {

        ReliefRequest request = service.getById(id);

        if (request == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(request);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approve(@PathVariable Long id) {

        return ResponseEntity.ok(service.approveRequest(id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<String> reject(@PathVariable Long id) {

        return ResponseEntity.ok(service.rejectRequest(id));
    }

    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<ReliefRequest>> getCitizenRequests(
            @PathVariable Long citizenId) {

        return ResponseEntity.ok(
                service.getRequestsByCitizen(citizenId)
        );

    }
}