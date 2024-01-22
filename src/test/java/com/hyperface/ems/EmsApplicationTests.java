package com.hyperface.ems;

import com.hyperface.ems.model.Department;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.model.Project;
import com.hyperface.ems.repository.DepartmentRepo;
import com.hyperface.ems.repository.EmployeeRepo;
import com.hyperface.ems.repository.ProjectRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
class EmsApplicationTests {
    @Autowired
    WebTestClient client;

    @MockBean
    private DepartmentRepo departmentRepo;

    @MockBean
    private ProjectRepo projectRepo;

    @MockBean
    private EmployeeRepo employeeRepo;

    @Test
    void employee_createEmployee_createSuccessfully() {
        client.post().uri("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"firstName\":\"Shashank\", \"lastName\":\"Varma\", \"email\":\"nshashankvarma@gmail.com\"}")
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testGetEmployeeDetails() {
        client.get()
                .uri("/api/employee/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Employee.class)
                .value(employeeResponse -> {
                    assertNotNull(employeeResponse.getId());
                    assertEquals("Shashank", employeeResponse.getFirstName());
                    assertEquals("nshashankvarma@gmail.com", employeeResponse.getEmail());
                });
    }

    @Test
    public void testAssignDepartment() {
        client.post()
                .uri("/api/employee/assignDept?employee=1&dept=101")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Successfully assigned");
    }


}