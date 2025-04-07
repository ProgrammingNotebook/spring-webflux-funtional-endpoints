# <img src="src/main/resources/logo.png" width="25" height="25"> Spring Webflux's Functional Endpoints

![Java](https://img.shields.io/badge/Java-22-blue.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

Exercise to understand the webflux's functional endpoints with production grade response, exceptional handling and unit tests. Functional endpoints are an alternative to the annotation-based programming model.

## Table of Contents
- [ Spring Webflux's Functional Endpoints](#-spring-webfluxs-functional-endpoints)
  - [Table of Contents](#table-of-contents)
  - [Architecture](#architecture)
  - [Gettings started with Employee management](#gettings-started-with-employee-management)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [Project Evolution](#project-evolution)
  - [References](#references)

## Architecture


  - ### Files of Importance

    ---
    
    **`EmployeeManagementRouter.java`**

    In webflux, an HTTP request is being handled by a `HandlerFunction`: A function that consumes `ServerRequest` and returns a `Mono<ServerResponse>`. `HandlerFunction` is similar to the body of the function marked with `@RequestMapping` annotation.

    `RouterFunctions.route()` provides a router builder that facilitates the creation of routers. 
    Our `EmployeeManagementRouter` is a `HandlerFunction` that uses `RouterFunctions` to creates the routes.
    <br/>
    <br/>

    ```java
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
    import static org.springframework.http.MediaType.APPLICATION_JSON;
    import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

    @Slf4j
    @Configuration
    public class EmployeeManagementRouter {

          @Bean
          public RouterFunction<ServerResponse> employeeRouter(EmployeeRequestHandler employeeRequestHandler) {

                  return RouterFunctions.route()
                      .path("/api/v1", builder -> builder.nest(accept(APPLICATION_JSON), routerBuilder -> routerBuilder
                          .GET("/employee/{id}", employeeRequestHandler::getEmployeeById)
                          .POST("/employee/create", employeeRequestHandler::createEmployee)))
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
                                                  .path(request.path()).build());
                      }).build();
          }
    }
    ```

    ---

    **`EmployeeRequestHandler.java`**

    Next we have `HandlerClass` that is used to expose the functionality of the routes. Although, you can write a `HandlerFunction` as a lambda, but to extend the functionality we will group the functionality into a `HandlerClass` whose role is similar to that of a `@Controller` in an annotation based application.

    Our `EmployeeRequestHandler` exposes the functionality to process the HTTP request of creating an employee and fetching the employee details.
    <br/>
    <br/>

    ```java
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
                                  .message(violation.getMessage()).build())
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
    ```

    ---

    <br/>

  - ### Sequence Diagram

  ![Sequence Diagram](/src/main/resources/Funtional%20Endpoint%20Sequence%20Diagram.svg)


<br/>

  ---

<br/>
<br/>

## Gettings started with Employee management

### Prerequisites
- Maven
- Java 17 SDK

### Installation
  - **Option 1: Docker**

    If you don't have docker CLI then run this command first

    ```java
    brew install docker
    ```

    Run the following command in the terminal to create the image in the docker
    ```bash
    docker build -t programming-notebook/spring-webflux-functional-endpoints:1.0 .
    ```

    This command will run the docker container on port 8080
    ```bash
    docker run -d -p 8080:8080 programming-notebook/spring-webflux-functional-endpoints:1.0
    ```
  - **Option 2: Locally**

    To run the project locally clone the repository and run the following command to build project
    ```bash
    mvn clean install
    ```

    Run this command to start the application 
    ```java
    mvn spring-boot:run
    ```

    I would recommend the use of an IDE rather than running the command directly in your console.

## Project Evolution

| Version | Changes                                                                                    | Status      |
| ------- | ------------------------------------------------------------------------------------------ | ----------- |
| v0.1    | Single endpoint to fetch the employee data using functional endpoint.                      | Completed   |
| v0.2    | Added exception handling to send proper status code in case of 404 or 5xx                  | Completed   |
| v0.3    | Added request validaton using Jakarta API                                                  | Completed   |
| v0.4    | Added Dockerfile to run the application in the docker container                            | Completed   |
| v0.4    | JUnit 5 unit test cases for testing the individual units using Spring webflux and Hamcrest | In Progress |

## References
- [Webflux Functional](https://docs.spring.io/spring-framework/reference/web/webflux-functional.html)
- [Spring 5 Functional Web](https://www.baeldung.com/spring-5-functional-web)