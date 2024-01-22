package com.hyperface.ems.integrationtest;

import com.hyperface.ems.model.Employee;
import com.hyperface.ems.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@Profile("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepoTest {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup(){
        employeeRepo.deleteAll();
    }


    @Test
    void existingEmployeeCanBeFound(){
        Employee employee = new Employee("Fn", "Ln", "email@email.com");

        int eId = entityManager.persist(employee).getId();
        Optional<Employee> employee1 = employeeRepo.findById(eId);

        assertThat(employee1.isPresent());

    }
}
