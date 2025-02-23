package com.example.spring_junit.service;

import com.example.spring_junit.model.Employee;
import com.example.spring_junit.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private Employee employee2;

    @BeforeEach
    void setUp(){
        employee = new Employee(1L, "devuser", "developer",10000.0);
        employee2 = new Employee(2L, "testuser", "tester",10000.0);
    }

    @Test
    void save(){

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee employee1 = employeeService.addEmployee(employee);

        assertNotNull(employee1);
        assertEquals(employee.getName(), employee1.getName());
    }

    @Test
    void getAllEmployees(){

        List<Employee> newList = new ArrayList<>();
        newList.add(employee);
        newList.add(employee2);

        when(employeeRepository.findAll()).thenReturn(newList);

        List<Employee> allEmployees = employeeService.getAllEmployees();

        assertEquals(2, allEmployees.size());
    }

    @Test
    void getEmloyeeById(){

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee employee1 = employeeService.getEmployeeById(employee.getId());

        assertNotNull(employee1);
        assertEquals(1L, employee1.getId());
    }

    @Test
    void updateEmployee(){

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = new Employee(1L, "newuser", "BA", 10000.0);
        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertEquals("newuser", result.getName());
    }

    @Test
    void deleteEmployee(){

       doNothing().when(employeeRepository).deleteById(employee.getId());

       employeeService.deleteEmployee(employee.getId());

       verify(employeeRepository, times(1)).deleteById(employee.getId());
    }

}