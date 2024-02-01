package com.hyperface.ems;

import com.hyperface.ems.model.Employee;
import com.hyperface.ems.repository.UserRepo;
import com.hyperface.ems.service.JwtService;
import com.hyperface.ems.service.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
class EmsApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private WebTestClient client;
    @Autowired
    private static UserInfoService userInfoService;
    @Autowired
    private static JwtService jwtService;
    @Autowired
    static private UserRepo userRepo;
    static String token;

    @BeforeEach
    void setup(){
//        client = MockMvcWebTestClient.bindToApplicationContext(context)
//                .apply(springSecurity())
//                .defaultRequest(get("/").with(csrf()))
//                .configureClient()
//                .build();
        client.post().uri("/auth/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"username\":\"admin\", \"password\":\"admin\", \"roles\":\"ROLE_ADMIN\"}")
                .exchange();
        token = client.post().uri("/auth/generateToken")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"username\":\"admin\", \"password\":\"admin\"}")
                .exchange()
                .expectBody(String.class)
                .returnResult().getResponseBody();
    }

    @Test
    void employee_createEmployee_createSuccessfully() {

        client.post().uri("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"firstName\":\"Shashank\", \"lastName\":\"Varma\", \"email\":\"nshashankvarma@gmail.com\"}")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .headers(http -> http.setBasicAuth("admin", "admin"))
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