FROM openjdk:25-jdk-oracle
COPY target/spring-webflux-functional-endpoints-1.0.jar /spring-webflux-functional-endpoints.jar
CMD ["java", "-jar", "/spring-webflux-functional-endpoints.jar"]
