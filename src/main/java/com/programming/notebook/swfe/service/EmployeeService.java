package com.programming.notebook.swfe.service;

import org.springframework.stereotype.Service;

import com.programming.notebook.swfe.exception.EmployeeNotFoundException;
import com.programming.notebook.swfe.model.Employee;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EmployeeService {

    public Mono<Employee> getEmployeeById(String id) {

        log.info("Fetching employee data with ID: {}", id);
        return switch (id) {
            case "E100" -> Mono.just(Employee.builder().id("E100").age(30).fullName("Harry").build());
            case "E101" -> Mono.just(Employee.builder().id("E101").age(31).fullName("John").build());
            case "E102" -> Mono.just(Employee.builder().id("E102").age(32).fullName("Helen").build());
            case "E103" -> Mono.just(Employee.builder().id("E103").age(33).fullName("Smith").build());
            default -> {
                log.error("Employee with ID: {} not found", id);
                yield Mono.error(new EmployeeNotFoundException("Employee with ID: " + id + " not found"));
            }
        };
    }

    public Mono<Employee> createEmployee(Employee employee) {
        
        log.info("Creating employee with ID: {}", employee.getId());
        return Mono.just(employee);
    }
}
