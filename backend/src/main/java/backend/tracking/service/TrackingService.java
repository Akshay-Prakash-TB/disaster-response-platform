package backend.tracking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import backend.incident.entity.Incident;
import backend.incident.repository.IncidentRepository;
import backend.resource.dto.OsrmResponse;
import backend.resource.entity.Resource;
import backend.resource.repository.ResourceRepository;
import backend.tracking.dto.RoutePoint;
import backend.tracking.entity.VehicleTracking;
import backend.tracking.repository.VehicleTrackingRepository;

@Service
public class TrackingService {

        @Autowired
        private VehicleTrackingRepository vehicleTrackingRepository;

        @Autowired
        private ResourceRepository resourceRepository;

        @Autowired
        private IncidentRepository incidentRepository;

private final RestClient
        restClient =
        RestClient.create();

    public VehicleTracking
initializeTracking(
        Long resourceId,
        Long incidentId) {

        Resource resource =
                resourceRepository
                        .findById(resourceId)
                        .orElseThrow();

        Incident incident =
                incidentRepository
                        .findById(incidentId)
                        .orElseThrow();

        String url =
                "https://router.project-osrm.org/route/v1/driving/"
                + resource.getLongitude()
                + ","
                + resource.getLatitude()
                + ";"
                + incident.getLongitude()
                + ","
                + incident.getLatitude()
                + "?overview=full&geometries=polyline";

        OsrmResponse response =
                restClient.get()
                        .uri(url)
                        .retrieve()
                        .body(
                                OsrmResponse.class);

        String geometry =
                response
                        .getRoutes()
                        .get(0)
                        .getGeometry();

        VehicleTracking tracking =
        vehicleTrackingRepository
                .findByResourceId(
                        resourceId)
                .orElse(
                        new VehicleTracking()
                );

        tracking.setResourceId(
                resourceId);

        tracking.setLatitude(
                resource.getLatitude());

        tracking.setLongitude(
                resource.getLongitude());

        tracking.setCurrentPointIndex(
                0);

        tracking.setRouteGeometry(
                geometry);

        tracking.setLastUpdated(
                LocalDateTime.now());

        return vehicleTrackingRepository
                .save(tracking);
    }

    public VehicleTracking
    getTracking(
            Long resourceId) {

        return vehicleTrackingRepository
                .findByResourceId(
                        resourceId)
                .orElse(null);
    }

    public VehicleTracking
updateLocation(
        Long resourceId,
        Double latitude,
        Double longitude) {

        VehicleTracking tracking =
                vehicleTrackingRepository
                        .findByResourceId(
                                resourceId)
                        .orElseThrow();

        tracking.setLatitude(
                latitude);

        tracking.setLongitude(
                longitude);

        tracking.setLastUpdated(
                LocalDateTime.now());

        return vehicleTrackingRepository
                .save(tracking);
    }

    private List<RoutePoint>
decodePolyline(
        String encoded) {

        List<RoutePoint> points =
                new ArrayList<>();

        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < encoded.length()) {

            int result = 1;
            int shift = 0;
            int b;

            do {
                b = encoded.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            }
            while (b >= 0x1f);

            lat += ((result & 1) != 0
                    ? ~(result >> 1)
                    : (result >> 1));

            result = 1;
            shift = 0;

            do {
                b = encoded.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            }
            while (b >= 0x1f);

            lng += ((result & 1) != 0
                    ? ~(result >> 1)
                    : (result >> 1));

            points.add(
                    new RoutePoint(
                            lat / 1E5,
                            lng / 1E5));
        }

        return points;
    }

    public int
countRoutePoints(
        Long resourceId) {

        VehicleTracking tracking =
                vehicleTrackingRepository
                        .findByResourceId(
                                resourceId)
                        .orElseThrow();

        return decodePolyline(
                tracking.getRouteGeometry())
                .size();
    }

    public VehicleTracking
moveVehicle(
        Long resourceId) {

        VehicleTracking tracking =
                vehicleTrackingRepository
                        .findByResourceId(
                                resourceId)
                        .orElseThrow();

        List<RoutePoint> points =
                decodePolyline(
                        tracking.getRouteGeometry());

        int index =
                tracking.getCurrentPointIndex();

        if (index >= points.size() - 1) {
            return tracking;
        }

        index++;

        RoutePoint point =
                points.get(index);

        tracking.setLatitude(
                point.getLatitude());

        tracking.setLongitude(
                point.getLongitude());

        tracking.setCurrentPointIndex(
                index);

        tracking.setLastUpdated(
                LocalDateTime.now());

        return vehicleTrackingRepository
                .save(tracking);
    }

    @Scheduled(fixedRate = 3000)
    public void autoMoveVehicles() {

        vehicleTrackingRepository
                .findAll()
                .forEach(tracking -> {

                    try {

                        moveVehicle(
                                tracking.getResourceId());

                    } catch (Exception e) {

                        System.out.println(
                                "Vehicle completed route");
                    }
                });
    }

    public List<VehicleTracking>getAllTracking() {
        return vehicleTrackingRepository
                .findAll();
        }

        public void stopTracking(
        Long resourceId) {

        VehicleTracking tracking =
                vehicleTrackingRepository
                        .findByResourceId(
                                resourceId)
                        .orElse(null);

        if(tracking != null) {

                vehicleTrackingRepository
                        .delete(tracking);
        }
        }
}