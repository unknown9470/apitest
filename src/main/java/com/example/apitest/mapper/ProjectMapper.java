package com.example.apitest.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.apitest.dto.ProjectDTO;
import com.example.apitest.dto.ProjectStepDTO;
import com.example.apitest.model.Project;
import com.example.apitest.model.ProjectStep;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {
        
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setOwnerUsername(project.getOwner().getUsername());
        dto.setDescription(project.getDescription());

        List<ProjectStepDTO> stepDTOs = project.getSteps()
            .stream()
            .map(ProjectMapper::mapStepToDTO)
            .collect(Collectors.toList());
        
        dto.setSteps(stepDTOs);
        return dto;
    }

    public static ProjectStepDTO mapStepToDTO(ProjectStep step) {

        ProjectStepDTO dto = new ProjectStepDTO();
        dto.setId(step.getId());
        dto.setName(step.getName());
        dto.setStatus(step.getStatus());
        if (step.getAssignedTo() != null) {
             dto.setAssignedToUsername(step.getAssignedTo().getUsername());
        }
        return dto;
    }
    
}
