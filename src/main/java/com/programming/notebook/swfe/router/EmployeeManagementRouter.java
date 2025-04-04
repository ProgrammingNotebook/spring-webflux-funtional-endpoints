package com.programming.notebook.swfe.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.programming.notebook.swfe.handler.EmployeeRequestHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class EmployeeManagementRouter {

    @Bean
    public RouterFunction<ServerResponse> employeeRouter(EmployeeRequestHandler employeeRequestHandler) {

        return RouterFunctions
                .route()
                .path("/api/v1", builder -> builder
                        .nest(accept(APPLICATION_JSON), routerBuilder -> routerBuilder
                                .GET("/employee/{id}", employeeRequestHandler::getEmployeeById)))
                .build();
    }
}
