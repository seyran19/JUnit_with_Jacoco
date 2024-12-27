package org.example.module1;

import org.example.dto.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/// оздаем 1 объек serviceTest для всех тестов
/// по умолчанию каждый раз создается новый объект класса для каждого теста
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLifestyleTest {


    private EmployeeService employeeService;

    @BeforeAll
    void init() {
        System.out.println("Before all " + this);
    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each " + this);
         employeeService = new EmployeeService();
    }


    @Test
    public void employeesEmptyIfNoEmployeesAdded() {
        System.out.println("Test1 " + this);
        List<Employee> allEmployees = employeeService.getAllEmployees();
        assertTrue(allEmployees.isEmpty(), "employee list should not be empty");

    }

    @Test
    void userSizeIfEmployeeAdded() {
        System.out.println("Test2 " + this);

        employeeService.add(new Employee());
        employeeService.add(new Employee());
        employeeService.add(new Employee());

        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(3, employees.size(), "employee list should not be empty");

    }

    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("afterEach " + this);

    }

    @AfterAll
     void afterAll() {
        System.out.println("afterAll " + this);
    }
}
