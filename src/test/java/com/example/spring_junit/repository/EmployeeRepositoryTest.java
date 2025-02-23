package com.example.spring_junit.repository;

import com.example.spring_junit.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp(){
        employee1 = new Employee(1L, "Devuser", "Developer", 10000.0);
        employee2 = new Employee(2L, "user", "BA", 10000.0);
//        employeeRepository.save(employee1);
//        employeeRepository.save(employee2);
    }

    @Test
    void saveEmployee(){
        employeeRepository.save(employee1);
        assertNotNull(employee1);
        assertEquals("Devuser", employee1.getName());
    }

    @Test
    void getEmployeeById(){
        employeeRepository.save(employee1);
        Optional<Employee> getEmployee = employeeRepository.findById(1L);
        System.out.println("Get employee"+getEmployee.get().getName());
        assertTrue(getEmployee.isPresent());
        assertEquals("Developer", getEmployee.get().getDepartment());
    }

    @Test
    void getAllEmployee(){
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<Employee> allEmployee = employeeRepository.findAll();
        assertEquals(2, allEmployee.size());
    }

    @Test
    void updateEmployee(){
        employeeRepository.save(employee2);
        employee2.setName("Testuser");
        employee2.setDepartment("QA");

        Employee updatedEmployee = employeeRepository.save(employee2);

        assertEquals("Testuser",employee2.getName());
        assertEquals("QA",employee2.getDepartment());
    }

    @Test
    void deleteEmployee(){
        employeeRepository.save(employee2);
        employeeRepository.deleteById(employee2.getId());
        Optional<Employee> deleteEmployee = employeeRepository.findById(employee2.getId());
        System.out.println(deleteEmployee.get().getName());
        assertFalse(deleteEmployee.isPresent());
    }


}