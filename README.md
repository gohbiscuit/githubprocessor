## Git Hub Repository Processor

This repository contains a simple file processing program built in Maven, compile in Java 21 with Junit 4 and
Lombok plugins. It uses annotation to
generate boilerplate code, such as getter and setter for clean code practices.

The source code is in the `src` folder, and test cases are located in `src/test` folder.

# Technologies

- Java 21
- Maven 4.0.0
- Spring Boot 3.3.0
- Spring Data JPA
- Hibernate
- H2 Database
- JUnit 4

## How to use

### Prerequisite

Built in Maven, compile in Java 21 with Junit 4.13.2 and Lombok plugins. Please install `IntelliJ IDEA` with `Java 21`
and `Maven`.

### Features

A REST service which will return details of given Github repository and cache the response in h2 database.

Sample endpoint `GET /repositories/{owner}/{repositoryname}` and gives the folowing data

```
{
"fullName": "...",
"description": "...",
"cloneUrl": "...",
"stars": 0,
"createdAt": "..."
}
```

`Error handling` Exceptions are thrown for invalid owner name and invalid repository details, if there are not fetched

### Resources folder

### application.properties

`server.port` is defaulted to 8080

`spring.jpa.hibernate.ddl-auto` is set to none

`spring.h2.console.enabled=true` is set to true for debugging purpose

### schema.sql

`schema.sql` denotes the creation of the table `GITHUB_REPOSITORY_DATA` in h2

### Building the program and running the tests

The program can be compiled by running ```mvn compile```.

The program can be built by running `mvn clean package`, which will also run the test cases.

### Compile and run GitHubProcessor

Navigate to target folder e.g. `githubprocessor/target` folder and run the following command:
`java -jar githubprocessor-0.0.1-SNAPSHOT.jar`

Download and install postman invoke `http://localhost:8080/repositories/{owner}/{repositoryname}` endpoint.

### Testing

Runs the unit and integration tests under resources folder.
Alternatively also visit `testing-evidences.excel` under root folder to view the testing process and results.
