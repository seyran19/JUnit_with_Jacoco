package org.example.module2;

import org.example.dto.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {

    private EmployeeService employeeService;

    public ExceptionTest() {
        this.employeeService = new EmployeeService();
    }

    @Test
    void throwsExceptionIfEmployeeIsNull() {
        Employee employee = new Employee();
        assertAll(
                () -> {
                    RuntimeException runtimeException = assertThrows(RuntimeException.class, employeeService::getAllEmployees);
                    assertThat(runtimeException).hasMessage("No employees");
                }
        );

    }
}
