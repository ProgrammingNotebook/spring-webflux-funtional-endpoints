package com.programming.notebook.swfe.handler;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.programming.notebook.swfe.dto.EmployeeTO;
import com.programming.notebook.swfe.dto.ErrorDetailTO;
import com.programming.notebook.swfe.dto.ErrorResponseTO;
import com.programming.notebook.swfe.model.Employee;
import com.programming.notebook.swfe.model.ErrorCode;
import com.programming.notebook.swfe.service.EmployeeService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeRequestHandler {

    private final Validator validator;
    private final EmployeeService employeeService;

    public Mono<ServerResponse> getEmployeeById(ServerRequest request) {

        String employeeId = request.pathVariable("id");
        log.info("Request to fetch employee data with ID: {}", employeeId);

        return employeeService
                .getEmployeeById(employeeId)
                .flatMap(employee -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(employee))
                .onErrorResume(e -> Mono.error(e));
    }

    public Mono<ServerResponse> createEmployee(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(EmployeeTO.class)
                .flatMap(request -> {

                    Set<ConstraintViolation<EmployeeTO>> violations = validator.validate(request);

                    if (!violations.isEmpty()) {

                        ErrorResponseTO errorResponse = ErrorResponseTO.builder()
                                .status("error")
                                .statusCode(HttpStatus.BAD_REQUEST)
                                .code(ErrorCode.INVALID_INPUT)
                                .message("Unable to validate the request")
                                .timestamp(ZonedDateTime.now())
                                .requestId(UUID.randomUUID().toString())
                                .path(serverRequest.path())
                                .errorDetails(violations.stream()
                                        .map(violation -> ErrorDetailTO.builder()
                                                .field(violation.getPropertyPath().toString())
                                                .message(violation.getMessage())
                                                .build())
                                        .toList())
                                .build();

                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(errorResponse);
                    }

                    return employeeService
                            .createEmployee(Employee.builder()
                                    .id(UUID.randomUUID().toString())
                                    .age(Integer.parseInt(request.getAge()))
                                    .fullName(request.getFullName()).build())
                            .flatMap(response -> ServerResponse
                                    .status(HttpStatus.CREATED)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(response));

                });
    }
}
