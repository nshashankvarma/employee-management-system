package com.hyperface.ems.controller;

import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import com.hyperface.ems.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private DepartmentRepo departmentRepo;
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public String createProject(@RequestBody Project project, @RequestParam(name = "department") int departmentId){
        Optional<Department> department1 = departmentRepo.findById(departmentId);
        if(department1.isEmpty()){
            return "Department of project not found!";
        }
        projectService.createProject(project, department1.get());
        return "Created New Project";
    }
}
