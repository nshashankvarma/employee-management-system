package com.hyperface.ems.service;

import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import com.hyperface.ems.repository.EmployeeRepo;
import com.hyperface.ems.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class EmployeeService {
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private ProjectRepo projectRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        super();
        this.employeeRepo = employeeRepo;
    }

    public void createEmployee(Employee employee){
        employeeRepo.save(employee);
    }

    public void assignDepartmentToEmployee(int empId, int deptId){
        Optional<Employee> employee= employeeRepo.findById(empId);
        Optional<Department> department= departmentRepo.findById(deptId);
        if(employee.isPresent() && department.isPresent()){
            Employee employee1 = employee.get();
            employee1.setDepartment(department.get());
            employeeRepo.save(employee1);
        }
    }

    public void assignProjectToEmployee(int empId, int projId){
        Optional<Employee> employee= employeeRepo.findById(empId);
        Optional<Project> project= projectRepo.findById(projId);
        if(employee.isPresent() && project.isPresent()){
            Employee employee1 = employee.get();
            Project project1 = project.get();
            if(employee1.getDepartment().getId() == project1.getDepartment().getId()) {
                employee1.setProject(project1);
                employeeRepo.save(employee1);
            }

        }
    }

    public String deleteEmployee(int empId){
        Optional<Employee> employee = employeeRepo.findById(empId);
        if(employee.isPresent()){
            employeeRepo.deleteById(empId);
            return "Deleted " + employee.get().getFirstName() + " Successfully";
        }
        return "Employee Not Found";
    }
}
