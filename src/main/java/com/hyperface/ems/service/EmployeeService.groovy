package com.hyperface.ems.service;

import com.hyperface.ems.exception.ApplicationException;
import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import com.hyperface.ems.repository.EmployeeRepo;
import com.hyperface.ems.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
class EmployeeService {
    private EmployeeRepo employeeRepo;

    private DepartmentRepo departmentRepo;

    private ProjectRepo projectRepo;

    EmployeeService(EmployeeRepo employeeRepo, DepartmentRepo departmentRepo, ProjectRepo projectRepo) {
        super();
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.projectRepo = projectRepo;
    }

    List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }
    Employee getEmployeeDetails(int empId){
        Optional<Employee> employee = employeeRepo.findById(empId);
        if(employee.isPresent()){
            return employee.get();
        }
        throw new ApplicationException(404, "Employee Not Found", "");
    }
    Employee createEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    void assignDepartmentToEmployee(int empId, int deptId){
        Optional<Employee> employee= employeeRepo.findById(empId);
        Optional<Department> department= departmentRepo.findById(deptId);
        if(employee.isPresent() && department.isPresent()){
            Employee employee1 = employee.get();
            employee1.setDepartment(department.get());
            employeeRepo.save(employee1);
        }
        else{
            throw new ApplicationException(404, "Invalid Parameters!", "Wrong Employee Id or Dept Id given!");
        }
    }

    void assignProjectToEmployee(int empId, int projId){
        Optional<Employee> employee= employeeRepo.findById(empId);
        Optional<Project> project= projectRepo.findById(projId);
        if(employee.isPresent() && project.isPresent()){
            Employee employee1 = employee.get();
            Project project1 = project.get();
            if(employee1.getDepartment().getId() == project1.getDepartment().getId()) {
                employee1.setProject(project1);
                employeeRepo.save(employee1);
            }
            else{
                throw new ApplicationException(404, "Invalid Parameters!","Project doesn't belong to his Department!");
            }
        }
        else{
            throw new ApplicationException(404, "Invalid Parameters!","Wrong Project Id or Dept Id given!");
        }
    }

    String deleteEmployee(int empId){
        Optional<Employee> employee = employeeRepo.findById(empId);
        if(employee.isPresent()){
            employeeRepo.deleteById(empId);
            return "Deleted " + employee.get().getFirstName() + " Successfully";
        }
        throw new ApplicationException(404, "Employee not found!","");
    }
}
