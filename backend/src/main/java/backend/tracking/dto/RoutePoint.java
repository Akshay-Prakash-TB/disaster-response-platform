package backend.tracking.dto;

public class RoutePoint {

    private Double latitude;
    private Double longitude;

    public RoutePoint(
            Double latitude,
            Double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}