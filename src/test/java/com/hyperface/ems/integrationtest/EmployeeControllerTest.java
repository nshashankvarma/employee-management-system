package com.hyperface.ems.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperface.ems.controller.EmployeeController;
import com.hyperface.ems.model.Employee;
import com.hyperface.ems.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;


    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void addEmployee() throws Exception {
        Employee employee = new Employee("Shashank", "Varma", "nshashankvarma@gmail.com");
        String jsonRequest = objectMapper.writeValueAsString(employee);
        MvcResult mvcResult = mockMvc.perform(post("/api/employee/create")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Employee Created Successfully", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void addInvalidEmployee() throws Exception{
        Employee employee = new Employee(null, "Varma", "nshashankvarma@gmail.com");
        String jsonRequest = objectMapper.writeValueAsString(employee);
        MvcResult mvcResult = mockMvc.perform(post("/api/employee/create")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getAllEmployee() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/employee/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

}
