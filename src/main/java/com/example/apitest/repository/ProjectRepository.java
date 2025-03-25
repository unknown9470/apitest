package com.example.apitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apitest.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}
