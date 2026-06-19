package backend.resource.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.incident.entity.Incident;
import backend.incident.repository.IncidentRepository;
import backend.resource.entity.Resource;
import backend.resource.repository.ResourceRepository;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private IncidentRepository incidentRepository;

    public Resource addResource(Resource resource) {

        resource.setStatus("AVAILABLE");

        return resourceRepository.save(resource);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource assignResource(Long resourceId,Long incidentId) {
        Resource resource =
            resourceRepository.findById(resourceId)
            .orElseThrow();

        Incident incident =
            incidentRepository.findById(incidentId)
            .orElseThrow();

        resource.setStatus("ASSIGNED");
        resource.setIncidentId(incidentId);

        incident.setStatus("ASSIGNED");

        incidentRepository.save(incident);

        return resourceRepository.save(resource);
    }
}