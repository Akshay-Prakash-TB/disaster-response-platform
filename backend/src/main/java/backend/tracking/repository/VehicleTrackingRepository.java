package backend.tracking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.tracking.entity.VehicleTracking;

public interface
VehicleTrackingRepository
extends JpaRepository<
        VehicleTracking,
        Long> {

    Optional<VehicleTracking>
    findByResourceId(
            Long resourceId);

        List<VehicleTracking> findAll();
}