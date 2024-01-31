package com.hyperface.ems.service;

import com.hyperface.ems.exception.ApplicationException;
import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class DepartmentService {
    private DepartmentRepo departmentRepo;

    DepartmentService(DepartmentRepo departmentRepo) {
        super();
        this.departmentRepo = departmentRepo;
    }

    void createDept(Department department){
        departmentRepo.save(department);
    }

    List<Project> getProjectsUnderDept(int deptId){
        Optional<Department> department = departmentRepo.findById(deptId);
        if(department.isPresent()){
            return department.get().getProjects();
        }
        throw new ApplicationException(404, "Department not found!","");
    }

    List<Employee> getEmployeesUnderDept(int deptId){
        Optional<Department> department = departmentRepo.findById(deptId);
        if(department.isPresent()){
            return department.get().getEmployees();
        }
        throw new ApplicationException(404, "Department not found!","");
    }

    @Transactional
    String deleteDepartment(int deptId){
        Optional<Department> department = departmentRepo.findById(deptId);
        if(department.isPresent()){
            List<Employee> employees = department.get().getEmployees();
            if(employees!=null){
                for(Employee e : employees){
                    e.setDepartment(null);
                    e.setProject(null);
                }
            }
            departmentRepo.deleteById(deptId);
            return "Deleted " + department.get().getName() + " Successfully";
        }
        throw new ApplicationException(404, "Department not found!","");
    }
}
