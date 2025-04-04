package com.programming.notebook.swfe.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.programming.notebook.swfe.model.Employee;
import com.programming.notebook.swfe.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmployeeRequestHandler {

    private final EmployeeService employeeService;

    // Add books
    public Mono<ServerResponse> getEmployeeById(ServerRequest request) {

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(employeeService.getEmployeeById(request.pathVariable("id")), Employee.class);
    }
}
