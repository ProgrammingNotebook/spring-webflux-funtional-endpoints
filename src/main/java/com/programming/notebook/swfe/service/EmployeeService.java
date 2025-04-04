package com.programming.notebook.swfe.service;

import org.springframework.stereotype.Service;

import com.programming.notebook.swfe.model.Employee;

import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    public Mono<Employee> getEmployeeById(String id) {

        return switch (id) {
            case "E100" -> Mono.just(Employee.builder().id("E100").age(30).fullName("Harry").build());
            case "E101" -> Mono.just(Employee.builder().id("E101").age(31).fullName("John").build());
            case "E102" -> Mono.just(Employee.builder().id("E102").age(32).fullName("Helen").build());
            case "E103" -> Mono.just(Employee.builder().id("E103").age(33).fullName("Smith").build());
            default -> Mono.error(new RuntimeException("Employee not found"));
        };
    }
}
