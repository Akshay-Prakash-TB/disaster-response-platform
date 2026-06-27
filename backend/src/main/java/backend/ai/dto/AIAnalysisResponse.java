package backend.ai.dto;

import java.util.Map;

public class AIAnalysisResponse {

    private String disasterType;

    private Double confidence;

    private Map<String, Integer> objects;

    private String summary;

    public AIAnalysisResponse() {
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Map<String, Integer> getObjects() {
        return objects;
    }

    public void setObjects(Map<String, Integer> objects) {
        this.objects = objects;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}