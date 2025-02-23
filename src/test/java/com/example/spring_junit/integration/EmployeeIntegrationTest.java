package com.example.spring_junit.integration;

import com.example.spring_junit.model.Employee;
import com.example.spring_junit.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private Employee employee1;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup(){

        baseUrl = baseUrl + ":" + port + "/employee";

        employee = new Employee(1L, "Devuser", "Developer", 10000.0);
        employee1 = new Employee(2L, "user", "BA", 10000.0);
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
    }

    @Test
    public void createEmployee(){

        Employee employee2 = restTemplate.postForObject(baseUrl, employee, Employee.class);

        assertNotNull(employee2);
        assertEquals(employee2.getName(), employee.getName());
    }

    @Test
    public void getAllEmployees(){
        List<Employee> listEmployee = restTemplate.getForObject(baseUrl, List.class);

        assertNotNull(listEmployee);
        assertEquals(2, listEmployee.size());
    }

    @Test
    public void getEmployeeById(){

        baseUrl = baseUrl + "/" + employee.getId();
        Employee employee2 = restTemplate.getForObject(baseUrl, Employee.class);

        assertNotNull(employee2);
        assertEquals(employee2.getDepartment(), employee.getDepartment());
    }

    @Test
    public void updateEmployee(){

        baseUrl = baseUrl + "/" + employee.getId();
        employee.setName("newuser");
        restTemplate.put(baseUrl, employee, Employee.class);

        Employee employee2 = restTemplate.getForObject(baseUrl, Employee.class);

        assertNotNull(employee2);
        assertEquals(employee.getName(), employee2.getName());
    }

    @Test
    public void deleteEmployee(){

        baseUrl = baseUrl + "/" + employee1.getId();
        restTemplate.delete(baseUrl);

       //Employee employee2 = restTemplate.getForObject(baseUrl, Employee.class);
        int count = employeeRepository.findAll().size();

        assertEquals(1, count);

        //assertNull(employee);
    }


}
