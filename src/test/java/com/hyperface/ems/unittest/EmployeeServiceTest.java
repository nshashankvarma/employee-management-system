package com.hyperface.ems.unittest;

import com.hyperface.ems.exception.ApplicationException;
import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import com.hyperface.ems.repository.EmployeeRepo;
import com.hyperface.ems.repository.ProjectRepo;
import com.hyperface.ems.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;
    @Mock
    private DepartmentRepo departmentRepo;
    @Mock
    private ProjectRepo projectRepo;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void getAllEmployees(){
        when(employeeRepo.findAll()).thenReturn(Stream.of(new Employee("Shashank", "Varma", "nshashankvarma@gmail.com"), new Employee("Ramesh", "Varma", "rameshvarma@gmail.com")).collect(Collectors.toList()));
        assertEquals(2, employeeService.getAllEmployees().size());
    }

    @Test
    public void createNewEmployee_success(){
        Employee employee = new Employee("Shashank", "Varma", "nshashankvarma@gmail.com");
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);
        assertEquals(employee, employeeService.createEmployee(employee));
    }

    @Test
    public void deleteExistingEmployee_success(){
        employeeService.createEmployee(new Employee("Shashank", "Varma", "nshashankvarma@gmail.com"));
        when(employeeRepo.findById(anyInt())).thenReturn(Optional.of(new Employee("Shashank", "Varma", "nshashankvarma@gmail.com")));
        employeeService.deleteEmployee(1);
        verify(employeeRepo, times(1)).deleteById(1);
    }

    @Test
    public void assignDept_success() {
        int empId = 1;
        int deptId = 101;
        Employee mockEmployee = new Employee();
        Department mockDepartment = new Department();

        when(employeeRepo.findById(empId)).thenReturn(Optional.of(mockEmployee));
        when(departmentRepo.findById(deptId)).thenReturn(Optional.of(mockDepartment));

        employeeService.assignDepartmentToEmployee(empId, deptId);

        verify(employeeRepo, times(1)).save(mockEmployee);
        assertNotNull(mockEmployee.getDepartment());
        assertEquals(mockDepartment, mockEmployee.getDepartment());
    }


    @Test
    public void assignDept_deptNotFound() {
        int empId = 1;
        int deptId = 101;

        when(employeeRepo.findById(empId)).thenReturn(Optional.empty());
        when(departmentRepo.findById(deptId)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            employeeService.assignDepartmentToEmployee(empId, deptId);
        });

        assertEquals("Invalid Parameters!", exception.getMessage());
    }

    @Test
    public void assignProjectToEmployee_success() {
        int empId = 1;
        int projId = 101;

        Employee mockEmployee = Mockito.mock(Employee.class);
        Project mockProject = Mockito.mock(Project.class);
        Department department = new Department(1, "");

        when(employeeRepo.findById(empId)).thenReturn(Optional.of(mockEmployee));
        when(projectRepo.findById(projId)).thenReturn(Optional.of(mockProject));

        when(mockEmployee.getDepartment()).thenReturn(department);
        when(mockProject.getDepartment()).thenReturn(department);

        employeeService.assignProjectToEmployee(empId, projId);

        verify(employeeRepo, times(1)).save(mockEmployee);

        assertNotNull(mockEmployee.getProject());
        assertEquals(mockProject, mockEmployee.getProject());
    }

    @Test
    public void assignProjectToEmployee_givenInvalidIds_notFound() {
        int empId = 1;
        int projId = 101;

        when(employeeRepo.findById(empId)).thenReturn(Optional.empty());
        when(projectRepo.findById(projId)).thenReturn(Optional.empty());
        System.out.println(employeeRepo.findById(1).isPresent());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            employeeService.assignProjectToEmployee(empId, projId);
        });

        assertEquals("Invalid Parameters!", exception.getMessage());
    }

    @Test
    public void deleteEmployee_success() {
        int empId = 1;

        Employee mockEmployee = new Employee();
        mockEmployee.setId(empId);
        mockEmployee.setFirstName("John");

        when(employeeRepo.findById(empId)).thenReturn(Optional.of(mockEmployee));

        String result = employeeService.deleteEmployee(empId);

        verify(employeeRepo, times(1)).deleteById(empId);

        assertEquals("Deleted John Successfully", result);
    }

    @Test
    public void deleteEmployee_givenInvalidId_NotFound() {
        int empId = 1;

        when(employeeRepo.findById(empId)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            employeeService.deleteEmployee(empId);
        });

        assertEquals("Employee not found!", exception.getMessage());
    }
}


