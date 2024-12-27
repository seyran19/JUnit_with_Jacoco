package org.example.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class Employee {

    int id;
    int age;
    private int salary;
    String name;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
