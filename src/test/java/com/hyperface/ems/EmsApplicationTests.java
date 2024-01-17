package com.hyperface.ems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
class EmsApplicationTests {
    WebTestClient client;

    @BeforeEach
    void setUp(ApplicationContext context) {
        client = MockMvcWebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void createEmployee(){
        client.post().uri("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"firstName\":\"Shashank\", \"lastName\":\"Varma\", \"email\":\"nshashankvarma@gmail.com\"}")
                .exchange()
                .expectStatus().isCreated();
    }
}