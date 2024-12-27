package org.example.module3;

import org.example.dto.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParametrizedTest {

    EmployeeService employeeService;

    @BeforeEach
    public void init() {
        employeeService = new EmployeeService();
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @DisplayName("tests1")
    void parametrizedTest(int countOfEmployees) {

        employeeService.add(new Employee());
        employeeService.add(new Employee());
        employeeService.add(new Employee());

        assertThat(employeeService.getAllEmployees().size()).isGreaterThan(countOfEmployees);

    }

    @ParameterizedTest
    @DisplayName("tests2")
    @MethodSource("getArgsForTest")
    void parametrizedTest2(int countOfEmployees) {

        employeeService.add(new Employee());
        employeeService.add(new Employee());
        employeeService.add(new Employee());

        assertThat(employeeService.getAllEmployees().size()).isGreaterThan(countOfEmployees);

    }

    static Stream<Arguments> getArgsForTest(){
        return Stream.of(Arguments.of(1), Arguments.of(2), Arguments.of(3));
    }
}
