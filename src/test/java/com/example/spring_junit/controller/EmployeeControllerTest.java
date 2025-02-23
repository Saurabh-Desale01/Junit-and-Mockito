package com.example.spring_junit.controller;

import com.example.spring_junit.model.Employee;
import com.example.spring_junit.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;
    private Employee employee1;

    @BeforeEach
    void setUp(){
        employee = new Employee(1L, "Devuser", "Developer", 10000.0);
        employee1 = new Employee(2L, "user", "BA", 10000.0);
    }

    @Test
    void createEmployee() throws Exception {

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Devuser"));
    }

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(employee, employee1);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employees.size()));
    }

    @Test
    void getEmployeeById() throws Exception {

        when(employeeService.getEmployeeById(employee.getId())).thenReturn(employee);

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Devuser"));
    }

    @Test
    void updateEmployee() throws Exception {
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Devuser"));

    }

    @Test
    void deleteEmployee() throws Exception {

        doNothing().when(employeeService).deleteEmployee(employee.getId());

        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isOk());
    }
}