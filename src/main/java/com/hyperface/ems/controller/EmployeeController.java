package com.hyperface.ems.controller;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        super();
        this.employeeService = employeeService;
    }

    @GetMapping("/getAll")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("{empId}")
    public Employee getEmployeeDetails(@PathVariable int empId){
        return employeeService.getEmployeeDetails(empId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createEmployee(@Valid @RequestBody Employee employee){
        employeeService.createEmployee(employee);
        return "Employee Created Successfully";
    }

    @PostMapping("/assignDept")
    public String assignDept(@RequestParam(name = "employee") int empId, @RequestParam(name = "dept") int deptId){
        employeeService.assignDepartmentToEmployee(empId, deptId);
        return "Successfully assigned";
    }

    @PostMapping("/assignProj")
    public String assignProj(@RequestParam(name = "employee") int empId, @RequestParam(name = "project") int projId){
        employeeService.assignProjectToEmployee(empId, projId);
        return "Successfully assigned";
    }

    @DeleteMapping("/delete/{employeeId}")
    public String deleteDept(@PathVariable("employeeId") int empId){
        return employeeService.deleteEmployee(empId);
    }

}
