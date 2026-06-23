package backend.tracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.tracking.entity.VehicleTracking;
import backend.tracking.service.TrackingService;

@RestController
@RequestMapping("/tracking")
@CrossOrigin("*")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @PostMapping("/start")
    public VehicleTracking startTracking(
            @RequestParam Long resourceId,
            @RequestParam Long incidentId) {

        return trackingService.initializeTracking(
                resourceId,
                incidentId);
    }

    @GetMapping("/{resourceId}")
    public VehicleTracking getTracking(
            @PathVariable Long resourceId) {

        return trackingService.getTracking(
                resourceId);
    }

    @GetMapping("/points/{resourceId}")
    public int countRoutePoints(
            @PathVariable Long resourceId) {

        return trackingService
                .countRoutePoints(
                        resourceId);
    }

    @PutMapping("/update")
    public VehicleTracking updateLocation(
            @RequestParam Long resourceId,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        return trackingService.updateLocation(
                resourceId,
                latitude,
                longitude);
    }

    @PostMapping("/move/{resourceId}")
    public VehicleTracking
    moveVehicle(
            @PathVariable Long resourceId) {

        return trackingService
                .moveVehicle(
                        resourceId);
    }

        @GetMapping("/all")
        public List<VehicleTracking>
        getAllTracking() {

        return trackingService
                .getAllTracking();
        }
}