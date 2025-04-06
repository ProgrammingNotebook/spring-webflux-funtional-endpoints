# <img src="src/main/resources/logo.png" width="25" height="25"> Spring Webflux's Functional Endpoints

![Java](https://img.shields.io/badge/Java-22-blue.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

Exercise to understand the webflux's functional endpoints with production grade response, exceptional handling and unit tests. Functional endpoints are an alternative to the annotation-based programming model.

## Table of Contents
- [ Spring Webflux's Functional Endpoints](#-spring-webfluxs-functional-endpoints)
  - [Table of Contents](#table-of-contents)
  - [Gettings started with Employee management](#gettings-started-with-employee-management)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [Project Evolution](#project-evolution)
  - [References](#references)

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