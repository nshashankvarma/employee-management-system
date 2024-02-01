package com.hyperface.ems.unittest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.ProjectRepo;
import com.hyperface.ems.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.hyperface.ems.exception.ApplicationException;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepo projectRepo;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void testGetProjectDeets_ExistingProject() {
        // Arrange
        int projId = 1;
        Project project = new Project(projId, "Project1", "Desc1");
        when(projectRepo.findById(projId)).thenReturn(Optional.of(project));

        // Act
        Optional<Project> result = projectService.getProjectDeets(projId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Project1", result.get().getProjectName());
    }

    @Test
    void testGetProjectDeets_NonExistingProject() {
        // Arrange
        int projId = 1;
        when(projectRepo.findById(projId)).thenReturn(Optional.empty());

        // Act & Assert
        Optional<Project> result = projectService.getProjectDeets(projId);
        assertFalse(result.isPresent());
    }

    // Similar tests for other methods...

    @Test
    void testDeleteProject_ExistingProject() {
        // Arrange
        int projId = 1;
        Project project = new Project(projId, "Project1", "Desc1");
        when(projectRepo.findById(projId)).thenReturn(Optional.of(project));

        // Act
        String result = projectService.deleteProject(projId);

        // Assert
        assertEquals("Deleted Project1 Successfully", result);
        verify(projectRepo, times(1)).deleteById(projId);
    }

    @Test
    void testDeleteProject_NonExistingProject() {
        // Arrange
        int projId = 1;
        when(projectRepo.findById(projId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApplicationException.class, () -> projectService.deleteProject(projId));
        verify(projectRepo, never()).deleteById(projId);
    }

    @Test
    void testGetEmployeesUnderProject_ExistingProject() {
        // Arrange
        int projId = 1;
        Project project = new Project(projId, "Project1", "Desc1");
        project.setEmployees(List.of(new Employee(1, "John", "Doe", "johndoe@email.com")));

        when(projectRepo.findById(projId)).thenReturn(Optional.of(project));

        // Act
        List<Employee> result = projectService.getEmployeesUnderProject(projId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testGetEmployeesUnderProject_NonExistingProject() {
        // Arrange
        int projId = 1;
        when(projectRepo.findById(projId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApplicationException.class, () -> projectService.getEmployeesUnderProject(projId));
    }
}