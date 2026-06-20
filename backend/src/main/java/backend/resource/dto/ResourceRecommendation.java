package backend.resource.dto;

public class ResourceRecommendation {

    private Long resourceId;
    private String resourceName;
    private String resourceType;
    private double distanceKm;
    private String eta;

    public ResourceRecommendation(
        Long resourceId,
        String resourceName,
        String resourceType,
        double distanceKm,
        String eta) {

        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.distanceKm = distanceKm;
        this.eta = eta;
    }

    public ResourceRecommendation(
            Long resourceId,
            String resourceName,
            String resourceType,
            double distanceKm) {

        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.distanceKm = distanceKm;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}