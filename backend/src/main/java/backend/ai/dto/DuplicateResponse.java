package backend.ai.dto;

import java.util.List;

public class DuplicateResponse {

    private Boolean duplicateFound;

    private Long bestMatchIncidentId;

    private Double bestSimilarityScore;

    private List<DuplicateMatch> matches;

    private String message;

    public DuplicateResponse() {
    }

    public Boolean getDuplicateFound() {
        return duplicateFound;
    }

    public void setDuplicateFound(
            Boolean duplicateFound) {

        this.duplicateFound =
                duplicateFound;

    }

    public Long getBestMatchIncidentId() {
        return bestMatchIncidentId;
    }

    public void setBestMatchIncidentId(
            Long bestMatchIncidentId) {

        this.bestMatchIncidentId =
                bestMatchIncidentId;

    }

    public Double getBestSimilarityScore() {
        return bestSimilarityScore;
    }

    public void setBestSimilarityScore(
            Double bestSimilarityScore) {

        this.bestSimilarityScore =
                bestSimilarityScore;

    }

    public List<DuplicateMatch>
    getMatches() {
        return matches;
    }

    public void setMatches(
            List<DuplicateMatch> matches) {

        this.matches = matches;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(
            String message) {

        this.message = message;

    }

}