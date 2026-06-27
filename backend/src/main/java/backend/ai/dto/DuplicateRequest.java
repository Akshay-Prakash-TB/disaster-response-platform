package backend.ai.dto;

import java.util.List;

public class DuplicateRequest {

    private String title;

    private String description;

    private List<ExistingIncidentDTO> existingIncidents;

    public DuplicateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(
            String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {
        this.description = description;
    }

    public List<ExistingIncidentDTO>
    getExistingIncidents() {
        return existingIncidents;
    }

    public void setExistingIncidents(
            List<ExistingIncidentDTO> existingIncidents) {

        this.existingIncidents =
                existingIncidents;

    }

}