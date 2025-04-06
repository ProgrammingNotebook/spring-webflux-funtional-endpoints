package com.programming.notebook.swfe.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.programming.notebook.swfe.dto.ErrorResponseTO;
import com.programming.notebook.swfe.exception.EmployeeNotFoundException;
import com.programming.notebook.swfe.handler.EmployeeRequestHandler;
import com.programming.notebook.swfe.model.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class EmployeeManagementRouter {

        @Bean
        public RouterFunction<ServerResponse> employeeRouter(EmployeeRequestHandler employeeRequestHandler) {

                log.info("Registering employee management router");
                return RouterFunctions
                                .route()
                                .path("/api/v1", builder -> builder
                                                .nest(accept(APPLICATION_JSON), routerBuilder -> routerBuilder
                                                                .GET("/employee/{id}",
                                                                                employeeRequestHandler::getEmployeeById)
                                                                .POST("/employee/create",
                                                                                employeeRequestHandler::createEmployee)))
                                .onError(EmployeeNotFoundException.class, (exception, request) -> {
                                        return ServerResponse
                                                        .status(HttpStatus.NOT_FOUND)
                                                        .contentType(APPLICATION_JSON)
                                                        .bodyValue(ErrorResponseTO.builder()
                                                                        .status("error")
                                                                        .statusCode(HttpStatus.NOT_FOUND)
                                                                        .code(ErrorCode.RESOURCE_NOT_FOUND)
                                                                        .message(exception.getMessage())
                                                                        .timestamp(ZonedDateTime.now())
                                                                        .requestId(UUID.randomUUID().toString())
                                                                        .path(request.path())
                                                                        .build());
                                })
                                .build();
        }
}
