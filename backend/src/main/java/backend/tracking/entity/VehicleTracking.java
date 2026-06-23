package backend.tracking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VehicleTracking {

    @Id
    @GeneratedValue(
        strategy =
        GenerationType.IDENTITY
    )
    private Long id;

    private Long resourceId;

    private Double latitude;

    private Double longitude;

    private LocalDateTime lastUpdated;

    private Integer currentPointIndex;

    @Column(columnDefinition = "TEXT")
    private String routeGeometry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(
            Long resourceId) {
        this.resourceId = resourceId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(
            Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(
            Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime
    getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(
            LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getCurrentPointIndex() {
        return currentPointIndex;
    }

    public void setCurrentPointIndex(
            Integer currentPointIndex) {
        this.currentPointIndex =
                currentPointIndex;
    }

    public String getRouteGeometry() {
        return routeGeometry;
    }

    public void setRouteGeometry(
            String routeGeometry) {
        this.routeGeometry =
                routeGeometry;
    }
}