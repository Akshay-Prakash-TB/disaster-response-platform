package backend.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.ai.entity.IncidentAIAnalysis;
import backend.ai.service.AIService;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class AIController {

    @Autowired
    private AIService aiService;

    @GetMapping("/incident/{incidentId}")
    public IncidentAIAnalysis getAnalysis(
            @PathVariable Long incidentId) {

        return aiService.getAnalysis(
                incidentId);

    }

}