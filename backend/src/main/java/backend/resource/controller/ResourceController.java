package backend.resource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.resource.dto.ResourceRecommendation;
import backend.resource.entity.Resource;
import backend.resource.service.ResourceService;

@RestController
@RequestMapping("/resource")
@CrossOrigin(origins = "http://localhost:5173")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/add")
    public Resource addResource(
            @RequestBody Resource resource) {

        return resourceService.addResource(resource);
    }

    @GetMapping("/all")
    public List<Resource> getAllResources() {
        return resourceService.getAllResources();
    }

    @PutMapping("/assign")
    public Resource assignResource(@RequestParam Long resourceId,@RequestParam Long incidentId) {
        return resourceService.assignResource(resourceId,incidentId);
    }

    @GetMapping("/recommend/{incidentId}")
    public List<ResourceRecommendation>
    recommendResources(
            @PathVariable Long incidentId) {

        return resourceService
                .recommendResources(
                        incidentId);
    }

    @GetMapping("/{id}")
    public Resource getResourceById(
            @PathVariable Long id) {

        return resourceService
                .getResourceById(id);
    }
}