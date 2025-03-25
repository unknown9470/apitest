package com.example.apitest.controller;

import com.example.apitest.dto.ProjectDTO;
import com.example.apitest.dto.ProjectStepDTO;
import com.example.apitest.service.ProjectService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestParam String name,@RequestParam String description,Authentication authentication) { 
        return ResponseEntity.ok(projectService.createProject(name, description, authentication.getName()));
    }

    @PostMapping("/{projectId}/steps")
    public ResponseEntity<ProjectStepDTO> addStep(
        @PathVariable Long projectId,
        @RequestParam String name,
        @RequestParam String assignedToUsername,
        Authentication authentication
    ) {
        
        return ResponseEntity.ok(projectService.addStep(projectId, name, assignedToUsername));
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }
  
}
