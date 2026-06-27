package backend.ai.dto;

public class DuplicateMatch {

    private Long incidentId;

    private Double similarityScore;

    public DuplicateMatch() {
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(
            Long incidentId) {
        this.incidentId = incidentId;
    }

    public Double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(
            Double similarityScore) {

        this.similarityScore =
                similarityScore;

    }

}