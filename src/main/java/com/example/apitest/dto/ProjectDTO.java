package com.example.apitest.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String ownerUsername;
    private List<ProjectStepDTO> steps;
}
