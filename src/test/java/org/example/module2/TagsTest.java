package org.example.module2;

import org.example.dto.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

/// мы можем потом запустить тесты отмеченные каким-то тегом

public class TagsTest {

    private EmployeeService employeeService;

    public TagsTest() {
        this.employeeService = new EmployeeService();
    }

    @Test
    @Tag("exception")
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
