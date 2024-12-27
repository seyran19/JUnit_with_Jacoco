package org.example.module2;

import org.example.dto.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/// оздаем 1 объек serviceTest для всех тестов
/// по умолчанию каждый раз создается новый объект класса для каждого теста
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AsserJTest {


    private EmployeeService employeeService;

    public AsserJTest() {
        this.employeeService = new EmployeeService();
    }

    @Test
    void employeeSizeIfEmployeeAdded() {
        System.out.println("Test2 " + this);

        employeeService.add(new Employee());
        employeeService.add(new Employee());
        employeeService.add(new Employee());

        List<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(3);
        assertThat(employees).isEqualTo(employeeService.getAllConvertedById());
    }

    @Test
    void employeeConvertedToMapById(){
        employeeService.add(new Employee(1, "Иван"));
        employeeService.add(new Employee(2, "Петр"));

        Map<Integer, Employee> map = employeeService.getAllConvertedById();

        assertAll(
                () -> assertThat(map).containsKeys(1, 2),
                () -> assertThat(map.get(1)).isInstanceOf(Employee.class),
                () -> assertThat(map.get(2)).isInstanceOf(Employee.class)
        );

    }


}
