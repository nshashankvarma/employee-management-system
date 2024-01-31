package com.hyperface.ems.service;

import com.hyperface.ems.exception.ApplicationException;
import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.ProjectRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ProjectService {


    private ProjectRepo projectRepo;

    ProjectService(ProjectRepo projectRepo) {
        super();
        this.projectRepo = projectRepo;
    }

    Optional<Project> getProjectDeets(int projId){
        return projectRepo.findById(projId);
    }

    void createProject(Project project, Department department){
        project.setDepartment(department);
        projectRepo.save(project);
    }

    String deleteProject(int projId){
        Optional<Project> project = projectRepo.findById(projId);
        if(project.isPresent()){
            List<Employee> employees = project.get().getEmployees();
            if(employees!=null){
                for(Employee e : employees){
                    e.setProject(null);
                }
            }
            projectRepo.deleteById(projId);
            return "Deleted " + project.get().getProjectName() + " Successfully";
        }
        throw new ApplicationException(404, "Project not found!","");
    }

    List<Employee> getEmployeesUnderProject(int projId){
        return projectRepo.findById(projId).get().getEmployees();
    }
}
