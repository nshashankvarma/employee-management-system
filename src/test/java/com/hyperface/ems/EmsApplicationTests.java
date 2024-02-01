package com.hyperface.ems;

import com.hyperface.ems.model.Employee;
import com.hyperface.ems.repository.UserRepo;
import com.hyperface.ems.service.JwtService;
import com.hyperface.ems.service.UserInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
class EmsApplicationTests {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private WebTestClient client;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    static String token;

    @BeforeEach
    void setup(){
        client.post().uri("/auth/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"username\":\"admin\", \"password\":\"admin\", \"roles\":\"ROLE_ADMIN\"}")
                .exchange()
                .expectStatus().isOk();

        token =  client.post()
                .uri("/auth/generateToken")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"username\": \"admin\", \"password\": \"admin\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
        System.out.println("Token: "+ token);
    }

    @AfterEach
    void cleanup(){
        userRepo.deleteAll();
    }

    @Test
    void employee_createEmployee_createSuccessfully() {

        client.post().uri("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"firstName\":\"Shashank\", \"lastName\":\"Varma\", \"email\":\"nshashankvarma@gmail.com\"}")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testGetEmployeeDetails() {
        client.get()
                .uri("/api/employee/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Employee.class)
                .value(employeeResponse -> {
                    assertEquals("Shashank", employeeResponse.getFirstName());
                    assertEquals("nshashankvarma@gmail.com", employeeResponse.getEmail());
                });
    }

    @Test
    public void testAssignDepartment() {
        client.post()
                .uri("/api/employee/assignDept?employee=1&dept=101")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Successfully assigned");
    }
}