package com.programming.notebook.swfe.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.programming.notebook.swfe.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmployeeRequestHandler {

    private final EmployeeService employeeService;

    // Add books
    public Mono<ServerResponse> getEmployeeById(ServerRequest request) {

        String employeeId = request.pathVariable("id");
        return employeeService.getEmployeeById(employeeId)
                .flatMap(employee -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(employee))
                .onErrorResume(e -> Mono.error(e));
    }
}
