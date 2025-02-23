package com.example.spring_junit.service;

import com.example.spring_junit.model.Employee;
import com.example.spring_junit.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee Not Found"));
    }

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee){
        Employee exstingEmployee = employeeRepository.findById(id).orElseThrow();
        //if(exstingEmployee.isPresent()){
            //Employee updateEmployee = new Employee();
            exstingEmployee.setName(employee.getName());
            exstingEmployee.setDepartment(employee.getDepartment());
            exstingEmployee.setSalary(employee.getSalary());

            return employeeRepository.save(exstingEmployee);
        //}
        //return null;
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

}
