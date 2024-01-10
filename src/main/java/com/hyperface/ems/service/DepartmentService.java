package com.hyperface.ems.service;

import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private DepartmentRepo departmentRepo;

    public DepartmentService(DepartmentRepo departmentRepo) {
        super();
        this.departmentRepo = departmentRepo;
    }

    public void createDept(Department department){
        departmentRepo.save(department);
    }

    public List<Project> getProjectsUnderDept(int deptId){
        Optional<Department> department = departmentRepo.findById(deptId);
        if(department.isPresent()){
            return department.get().getProjects();
        }
        return null;
    }

    public List<Employee> getEmployeesUnderDept(int deptId){
        Optional<Department> department = departmentRepo.findById(deptId);
        if(department.isPresent()){
            return department.get().getEmployees();
        }
        return null;
    }

    public String deleteDepartment(int deptId){
        Optional<Department> department = departmentRepo.findById(deptId);
        if(department.isPresent()){
            departmentRepo.deleteById(deptId);
            return "Deleted " + department.get().getName() + " Successfully";
        }
        return "Department Not Found";
    }
}
