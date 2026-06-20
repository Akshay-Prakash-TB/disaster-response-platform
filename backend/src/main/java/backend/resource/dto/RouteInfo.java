package backend.resource.dto;

public class RouteInfo {

    private double distanceMeters;
    private double durationSeconds;

    public RouteInfo(
            double distanceMeters,
            double durationSeconds) {

        this.distanceMeters = distanceMeters;
        this.durationSeconds = durationSeconds;
    }

    public double getDistanceMeters() {
        return distanceMeters;
    }

    public double getDurationSeconds() {
        return durationSeconds;
    }
}