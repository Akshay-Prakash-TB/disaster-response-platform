package backend.shelter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.shelter.dto.ShelterRequest;
import backend.shelter.dto.ShelterResponse;
import backend.shelter.service.ShelterService;

@RestController
@RequestMapping("/shelter")
@CrossOrigin(origins = "http://localhost:5173")
public class ShelterController {

    @Autowired
    private ShelterService shelterService;

    @PostMapping("/add")
    public ShelterResponse addShelter(
            @RequestBody ShelterRequest request) {

        return shelterService.createShelter(request);
    }

    @GetMapping("/all")
    public List<ShelterResponse> getAllShelters() {

        return shelterService.getAllShelters();
    }

    @GetMapping("/{id}")
    public ShelterResponse getShelterById(
            @PathVariable Long id) {

        return shelterService.getShelterById(id);
    }

    @PutMapping("/update/{id}")
    public ShelterResponse updateShelter(
            @PathVariable Long id,
            @RequestBody ShelterRequest request) {

        return shelterService.updateShelter(id, request);
    }

    @PostMapping("/delete/{id}")
    public String deleteShelter(
            @PathVariable Long id) {

        return shelterService.deleteShelter(id);
    }

    @GetMapping("/ngo/{ngoId}")
    public List<ShelterResponse> getSheltersByNgo(
            @PathVariable Long ngoId) {

        return shelterService.getSheltersByNgo(ngoId);
    }

    @GetMapping("/available")
    public List<ShelterResponse> getAvailableShelters() {

        return shelterService.getAvailableShelters();

    }
}