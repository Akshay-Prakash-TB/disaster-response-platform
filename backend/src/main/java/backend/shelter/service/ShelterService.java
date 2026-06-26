package backend.shelter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.shelter.dto.ShelterRequest;
import backend.shelter.dto.ShelterResponse;
import backend.shelter.entity.Shelter;
import backend.shelter.repository.ShelterRepository;

@Service
public class ShelterService {

    @Autowired
    private ShelterRepository shelterRepository;

    public ShelterResponse createShelter(ShelterRequest request) {

        Shelter shelter = new Shelter();

        shelter.setNgoId(request.getNgoId());
        shelter.setName(request.getName());
        shelter.setLatitude(request.getLatitude());
        shelter.setLongitude(request.getLongitude());
        shelter.setCapacity(request.getCapacity());
        shelter.setCurrentOccupancy(request.getCurrentOccupancy());
        shelter.setStatus(request.getStatus());

        Shelter savedShelter = shelterRepository.save(shelter);

        return convertToResponse(savedShelter);
    }

    public List<ShelterResponse> getAllShelters() {

        List<Shelter> shelters =
                shelterRepository.findAll();

        List<ShelterResponse> response =
                new ArrayList<>();

        for (Shelter shelter : shelters) {

            response.add(convertToResponse(shelter));

        }

        return response;
    }

    public List<ShelterResponse> getSheltersByNgo(Long ngoId) {

        List<Shelter> shelters = shelterRepository.findByNgoId(ngoId);

        List<ShelterResponse> responseList = new ArrayList<>();

        for (Shelter shelter : shelters) {

            responseList.add(convertToResponse(shelter));

        }

        return responseList;
    }

    public ShelterResponse getShelterById(Long id) {

        Optional<Shelter> optionalShelter = shelterRepository.findById(id);

        if (optionalShelter.isEmpty()) {

            return null;

        }

        return convertToResponse(optionalShelter.get());

    }

    public ShelterResponse updateShelter(Long id, ShelterRequest request) {

        Optional<Shelter> optionalShelter =
                shelterRepository.findById(id);

        if (optionalShelter.isEmpty()) {
            return null;
        }

        Shelter shelter = optionalShelter.get();

        shelter.setName(request.getName());

        shelter.setLatitude(request.getLatitude());

        shelter.setLongitude(request.getLongitude());

        shelter.setCapacity(request.getCapacity());

        shelter.setCurrentOccupancy(
                request.getCurrentOccupancy());

        shelter.setStatus(request.getStatus());

        // Do NOT update ngoId.
        // The shelter remains owned by the NGO that created it.

        Shelter updatedShelter =
                shelterRepository.save(shelter);

        return convertToResponse(updatedShelter);

    }

    public String deleteShelter(Long id) {

        Optional<Shelter> optionalShelter = shelterRepository.findById(id);

        if (optionalShelter.isEmpty()) {

            return "Shelter not found";

        }

        shelterRepository.deleteById(id);

        return "Shelter deleted successfully";

    }

    private ShelterResponse convertToResponse(Shelter shelter) {

        ShelterResponse response =
                new ShelterResponse();

        response.setId(shelter.getId());

        response.setNgoId(shelter.getNgoId());

        response.setName(shelter.getName());

        response.setLatitude(shelter.getLatitude());

        response.setLongitude(shelter.getLongitude());

        response.setCapacity(shelter.getCapacity());

        response.setCurrentOccupancy(
                shelter.getCurrentOccupancy());

        response.setStatus(shelter.getStatus());

        return response;
    }

    public List<ShelterResponse> getAvailableShelters() {

        List<Shelter> shelters =
                shelterRepository.findByStatus("OPEN");

        List<ShelterResponse> response =
                new ArrayList<>();

        for (Shelter shelter : shelters) {

            if (shelter.getCurrentOccupancy() < shelter.getCapacity()) {

                response.add(convertToResponse(shelter));

            }

        }

        return response;
    }
}