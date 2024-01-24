package com.hyperface.ems.controller;


import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DepartmentController {
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        super();
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    public String createDepartment(@Valid @RequestBody Department department){
        departmentService.createDept(department);
        return "Created new Department";
    }

    @GetMapping("/getProjects/{departmentId}")
    public List<Project> getProjectsUnderDept(@PathVariable("departmentId") int deptId){
        return departmentService.getProjectsUnderDept(deptId);
    }

    @GetMapping("/getEmployees/{departmentId}")
    public List<Employee> getEmployeesUnderDept(@PathVariable("departmentId") int deptId){
        return departmentService.getEmployeesUnderDept(deptId);
    }

    @DeleteMapping("/delete/{departmentId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteDept(@PathVariable("departmentId") int deptId){
        return departmentService.deleteDepartment(deptId);
    }
}