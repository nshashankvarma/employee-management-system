package com.hyperface.ems.unittest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.hyperface.ems.exception.ApplicationException;
import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import com.hyperface.ems.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void testCreateDept() {
        // Arrange
        Department department = new Department();

        // Act
        departmentService.createDept(department);

        // Assert
        verify(departmentRepo, times(1)).save(department);
    }

    @Test
    void testGetProjectsUnderDept_ExistingDepartment() {
        // Arrange
        int deptId = 1;
        Department department = new Department(deptId, "IT");
        department.setProjects(List.of(new Project(1, "Project1", "")));
        department.setProjects(List.of(new Project(1, "Project1", "Desc1")));

        when(departmentRepo.findById(deptId)).thenReturn(Optional.of(department));

        // Act
        List<Project> result = departmentService.getProjectsUnderDept(deptId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Project1", result.get(0).getProjectName());
    }

    @Test
    void testGetProjectsUnderDept_NonExistingDepartment() {
        // Arrange
        int deptId = 1;
        when(departmentRepo.findById(deptId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApplicationException.class, () -> departmentService.getProjectsUnderDept(deptId));
    }

    // Similar tests for other methods...

    @Test
    void testDeleteDepartment_ExistingDepartment() {
        // Arrange
        int deptId = 1;
        Department department = new Department(deptId, "IT");
        when(departmentRepo.findById(deptId)).thenReturn(Optional.of(department));

        // Act
        String result = departmentService.deleteDepartment(deptId);

        // Assert
        assertEquals("Deleted IT Successfully", result);
        verify(departmentRepo, times(1)).deleteById(deptId);
    }

    @Test
    void testDeleteDepartment_NonExistingDepartment() {
        // Arrange
        int deptId = 1;
        when(departmentRepo.findById(deptId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApplicationException.class, () -> departmentService.deleteDepartment(deptId));
        verify(departmentRepo, never()).deleteById(deptId);
    }
}