package com.example.apitest.dto;

import lombok.Data;

@Data
public class ProjectStepDTO {
    
    private Long id;
    private String name;
    private String status;
    private String assignedToUsername;
    
}
