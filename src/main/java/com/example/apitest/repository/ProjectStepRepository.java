package com.example.apitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apitest.model.ProjectStep;

@Repository
public interface ProjectStepRepository extends JpaRepository<ProjectStep, Long> {
    
}
