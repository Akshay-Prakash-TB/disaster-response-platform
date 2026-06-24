package backend.assignment.dto;

public class ActiveMissionDTO {

    private Long assignmentId;

    private String incidentTitle;

    private String resourceName;

    private String rescueTeam;

    private String status;

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getIncidentTitle() {
        return incidentTitle;
    }

    public void setIncidentTitle(String incidentTitle) {
        this.incidentTitle = incidentTitle;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getRescueTeam() {
        return rescueTeam;
    }

    public void setRescueTeam(String rescueTeam) {
        this.rescueTeam = rescueTeam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}