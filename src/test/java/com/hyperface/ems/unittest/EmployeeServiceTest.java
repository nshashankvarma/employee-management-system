package com.hyperface.ems.unittest;

import com.hyperface.ems.model.Employee;
import com.hyperface.ems.repository.EmployeeRepo;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setup(){
        employeeRepo = Mockito.mock(EmployeeRepo.class);
        employeeService = new EmployeeService(employeeRepo);
    }

    @Test
    public void getAllEmployees(){
        when(employeeRepo.findAll()).thenReturn(Stream.of(new Employee("Shashank", "Varma", "nshashankvarma@gmail.com"), new Employee("Ramesh", "Varma", "rameshvarma@gmail.com")).collect(Collectors.toList()));
        assertEquals(2, employeeService.getAllEmployees().size());
    }

    @Test
    public void createNewEmployee(){
        Employee employee = new Employee("Shashank", "Varma", "nshashankvarma@gmail.com");
        when(employeeRepo.save(employee)).thenReturn(employee);
        assertEquals(employee, employeeService.createEmployee(employee));
    }

    @Test
    public void deleteEmployee(){
        employeeService.createEmployee(new Employee("Shashank", "Varma", "nshashankvarma@gmail.com"));
        when(employeeRepo.findById(1)).thenReturn(Optional.of(new Employee("Shashank", "Varma", "nshashankvarma@gmail.com")));
        employeeService.deleteEmployee(1);
        verify(employeeRepo, times(1)).deleteById(1);
    }
}
