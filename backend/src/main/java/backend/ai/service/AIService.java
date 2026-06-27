package backend.ai.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import backend.ai.dto.AIAnalysisResponse;
import backend.ai.dto.DuplicateRequest;
import backend.ai.dto.DuplicateResponse;
import backend.ai.dto.ExistingIncidentDTO;
import backend.ai.entity.IncidentAIAnalysis;
import backend.ai.repository.IncidentAIAnalysisRepository;
import backend.incident.entity.Incident;

@Service
public class AIService {

    @Autowired
    private IncidentAIAnalysisRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String AI_URL =
            "http://localhost:8000/analyze-image";

    public void analyzeIncidentImage(
            Long incidentId,
            String imageName) {

        try {

            File image =
                    new File("uploads/" + imageName);

            MultiValueMap<String, Object> body =
                    new LinkedMultiValueMap<>();

            body.add(
                    "image",
                    new FileSystemResource(image));

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.MULTIPART_FORM_DATA);

            HttpEntity<
                    MultiValueMap<String, Object>>
                    request =
                    new HttpEntity<>(
                            body,
                            headers);

            ResponseEntity<AIAnalysisResponse>
                    response =
                    restTemplate.postForEntity(
                            AI_URL,
                            request,
                            AIAnalysisResponse.class);

            AIAnalysisResponse ai =
                    response.getBody();

            if (ai == null) {
                return;
            }

            IncidentAIAnalysis analysis =
                    new IncidentAIAnalysis();

            analysis.setIncidentId(
                    incidentId);

            analysis.setDisasterType(
                    ai.getDisasterType());

            analysis.setConfidence(
                    ai.getConfidence());

            StringBuilder builder = new StringBuilder();

            for (String key : ai.getObjects().keySet()) {

                builder.append(key)
                    .append(" (")
                    .append(ai.getObjects().get(key))
                    .append("), ");
            }

            analysis.setDetectedObjects(builder.toString());

            analysis.setSummary(
                    ai.getSummary());

            repository.save(
                    analysis);

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    public IncidentAIAnalysis getAnalysis(Long incidentId) {

        return repository
                .findByIncidentId(incidentId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "AI analysis not found"));

    }

    public DuplicateResponse checkDuplicate(

        Incident newIncident,

                List<Incident> nearbyIncidents

        ) {

        DuplicateRequest request =
                new DuplicateRequest();

        request.setTitle(
                newIncident.getTitle());

        request.setDescription(
                newIncident.getDescription());

        List<ExistingIncidentDTO> existing =
                new ArrayList<>();

        for (Incident incident : nearbyIncidents) {

                ExistingIncidentDTO dto =
                        new ExistingIncidentDTO();

                dto.setId(
                        incident.getId());

                dto.setTitle(
                        incident.getTitle());

                dto.setDescription(
                        incident.getDescription());

                existing.add(dto);

        }

        request.setExistingIncidents(
                existing);

        ResponseEntity<DuplicateResponse> response =
                restTemplate.exchange(

                        "http://localhost:8000/check-duplicate",

                        HttpMethod.POST,

                        new HttpEntity<>(request),

                        DuplicateResponse.class

                );

        return response.getBody();

        }

}