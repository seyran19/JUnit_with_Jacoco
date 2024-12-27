package org.example.service;

import lombok.Data;
import org.example.dto.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

    public List<Employee> getAllEmployees() {
        if (employees.isEmpty()) {
            throw new RuntimeException("No employees");
        }
        return employees;
    }

    public boolean add(Employee employee) {
        employees.add(employee);
        return true;
    }

    public Map<Integer, Employee> getAllConvertedById() {
       return  employees.stream()
                .collect(Collectors.toMap(Employee::getId, employee -> employee));
    }
}
