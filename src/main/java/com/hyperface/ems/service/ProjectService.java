package com.hyperface.ems.service;

import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import com.hyperface.ems.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {


    private ProjectRepo projectRepo;

    public ProjectService(ProjectRepo projectRepo) {
        super();
        this.projectRepo = projectRepo;
    }

    public void createProject(Project project, Department department){
        project.setDepartment(department);
        projectRepo.save(project);
    }
}
