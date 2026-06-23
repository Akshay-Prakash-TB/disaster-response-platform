package backend.resource.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import backend.assignment.service.AssignmentService;
import backend.incident.entity.Incident;
import backend.incident.repository.IncidentRepository;
import backend.resource.dto.OsrmResponse;
import backend.resource.dto.ResourceRecommendation;
import backend.resource.dto.RouteInfo;
import backend.resource.entity.Resource;
import backend.resource.repository.ResourceRepository;
import backend.tracking.service.TrackingService;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private TrackingService trackingService;

    private final RestClient restClient =
            RestClient.create();

    public Resource addResource(Resource resource) {

        resource.setStatus("AVAILABLE");

        return resourceRepository.save(resource);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource assignResource(
        Long resourceId,
        Long incidentId) {

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

        assignmentService.createAssignment(
                incidentId,
                resourceId);

        return resourceRepository.save(resource);
        }

    public RouteInfo getRouteInfo(
            double startLat,
            double startLon,
            double endLat,
            double endLon) {

        String url =
                "https://router.project-osrm.org/route/v1/driving/"
                        + startLon + "," + startLat + ";"
                        + endLon + "," + endLat
                        + "?overview=false";

        OsrmResponse response =
                restClient.get()
                        .uri(url)
                        .retrieve()
                        .body(OsrmResponse.class);

        if (response == null
                || response.getRoutes() == null
                || response.getRoutes().isEmpty()) {

            return new RouteInfo(
                    Double.MAX_VALUE,
                    Double.MAX_VALUE);
        }

        OsrmResponse.Route route =
                response.getRoutes().get(0);

        return new RouteInfo(
                route.getDistance(),
                route.getDuration());
    }

    private String formatEta(
            double durationSeconds) {

        long totalMinutes =
                Math.round(durationSeconds / 60);

        long hours =
                totalMinutes / 60;

        long minutes =
                totalMinutes % 60;

        if (hours == 0) {
            return minutes + " min";
        }

        return hours + " hr "
                + minutes + " min";
    }

    public List<ResourceRecommendation>
    recommendResources(Long incidentId) {

        Incident incident =
                incidentRepository.findById(incidentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Incident not found"));

        String requiredType =
                switch (incident.getIncidentType()) {

                    case "FLOOD" -> "BOAT";
                    case "MEDICAL" -> "AMBULANCE";
                    case "RESCUE" -> "VOLUNTEER";

                    default ->
                            throw new RuntimeException(
                                    "Unknown incident type");
                };

        List<Resource> resources =
                resourceRepository.findByStatus(
                        "AVAILABLE");

        List<ResourceRecommendation>
                recommendations =
                new ArrayList<>();

        for (Resource resource : resources) {

            if (!resource.getType()
                    .equals(requiredType)) {
                continue;
            }

            if (resource.getLatitude() == null
                    || resource.getLongitude() == null) {
                continue;
            }

            RouteInfo routeInfo =
                    getRouteInfo(
                            resource.getLatitude(),
                            resource.getLongitude(),
                            incident.getLatitude(),
                            incident.getLongitude());

            recommendations.add(
                    new ResourceRecommendation(
                            resource.getId(),
                            resource.getName(),
                            resource.getType(),
                            routeInfo.getDistanceMeters() / 1000.0,
                            formatEta(
                                    routeInfo.getDurationSeconds())
                    ));
        }

        recommendations.sort(
                Comparator.comparingDouble(
                        ResourceRecommendation::getDistanceKm));

        if (recommendations.size() > 3) {
            return recommendations.subList(0, 3);
        }

        return recommendations;
    }

    public Resource getResourceById(
        Long id) {

        return resourceRepository
                .findById(id)
                .orElseThrow();
    }
}