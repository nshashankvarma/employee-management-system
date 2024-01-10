package com.hyperface.ems.service;

import com.hyperface.ems.exception.ApplicationException;
import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.ProjectRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {


    private ProjectRepo projectRepo;

    public ProjectService(ProjectRepo projectRepo) {
        super();
        this.projectRepo = projectRepo;
    }

    public Optional<Project> getProjectDeets(int projId){
        return projectRepo.findById(projId);
    }

    public void createProject(Project project, Department department){
        project.setDepartment(department);
        projectRepo.save(project);
    }

    public String deleteProject(int projId){
        Optional<Project> project = projectRepo.findById(projId);
        if(project.isPresent()){
            projectRepo.deleteById(projId);
            return "Deleted " + project.get().getProjectName() + " Successfully";
        }
        throw new ApplicationException(404, "Project not found!");
    }
}
