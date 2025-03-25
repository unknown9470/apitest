package com.example.apitest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.apitest.dto.ProjectDTO;
import com.example.apitest.dto.ProjectStepDTO;
import com.example.apitest.mapper.ProjectMapper;
import com.example.apitest.model.Project;
import com.example.apitest.model.ProjectStep;
import com.example.apitest.repository.ProjectRepository;
import com.example.apitest.repository.ProjectStepRepository;
import com.example.apitest.repository.UserRepository;
import com.example.apitest.model.User;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectStepRepository stepRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectStepRepository stepRepository,
                          UserRepository userRepository,
                          NotificationService notificationService) {
        this.projectRepository = projectRepository;
        this.stepRepository = stepRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public ProjectDTO createProject(String name, String description, String ownerUsername) {

        User owner = userRepository.findByUsername(ownerUsername).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setOwner(owner);
        
        Project saved = projectRepository.save(project);
        return ProjectMapper.toDTO(saved);

    }

    public ProjectStepDTO addStep(Long projectId, String stepName, String assignedToUsername){
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Aucun projet trouvé"));
        User assignedTo = userRepository.findByUsername(assignedToUsername)
            .orElseThrow(() -> new RuntimeException("Utilisateur à assigner introuvable"));
        
        ProjectStep step = new ProjectStep();
        step.setName(stepName);
        step.setProject(project);
        step.setAssignedTo(assignedTo);

        ProjectStep savedStep = stepRepository.save(step);

        String message = "Une nouvelle tâche '" + stepName + "' vous a été assignée dans le projet '" + project.getName() + "'.";
        notificationService.send("email", assignedTo.getUsername(), message, project.getOwner().getUsername());
        
        return ProjectMapper.mapStepToDTO(savedStep);
    }

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(ProjectMapper::toDTO).toList();
    }

    public Optional<ProjectDTO> getById(Long id) {
        return projectRepository.findById(id).map(ProjectMapper::toDTO);
    }
    

}
