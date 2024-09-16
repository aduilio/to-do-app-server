[![Build Status](https://ci-cd.springdoc.org:8443/buildStatus/icon?job=springdoc-demos%2F2.x)](https://ci-cd.springdoc.org:8443/job/springdoc-demos/job/2.x/)

# To-Do App Server

## Running the application

You can run directly on the IDE as a Spring Boot application.
It is possible to run using the Maven command: *mvn spring-boot:run*.

Run using the jar file: *java -jar bin/application.jar*.

## Running using Docker

Build the image using the command: *docker build -t posinatel/todoappserver .* (pay attention the dot in the end of the command)

Check the image using the command: *docker images*

Run the application using the command: *docker run -p 8080:8080 -t posinatel/todoappserver*

## H2 Database

To access the H2 database console: 
- URL: http://localhost:8080/h2
- Username: sa
- Password:

A file named *database* will be created on the folder *~/.ToDoApp/database*.

## Swagger Documentation

After running the application, the documentation is available on http://localhost:8080/swagger-ui/index.html#/.

## **POST /tasks**

Create a task.

Request
```json
{
    "title": "My Title",
    "description": "My Description",
    "date": "2024-02-20",
    "time": "10:45"
}
```
Response: 201 Created
```json
{
    "id": 1,
    "title": "My Title",
    "description": "My Description",
    "date": "2024-02-20",
    "time": "10:45",
    "completed": false
}
```

## **GET /tasks**

Retrieve all tasks.

Response: 200 OK
```json
[{
    "id": 1,
    "title": "My Title",
    "description": "My Description",
    "date": "2024-02-20",
    "time": "10:45",
    "completed": false
}]
```

## **GET /tasks/{id}**

Retrieve a task.

Response: 200 OK
```json
{
    "id": 1,
    "title": "My Title",
    "description": "My Description",
    "date": "2024-02-20",
    "time": "10:45",
    "completed": false
}
```

## **PATCH /tasks/{id}**

Update a task.

Request
```json
{
    "title": "My New Title",
    "description": "My New Description",
    "date": "2024-04-20",
    "time": "14:45"
}
```
Response: 200 OK
```json
{
    "id": 1,
    "title": "My New Title",
    "description": "My New Description",
    "date": "2024-04-20",
    "time": "14:45",
    "completed": false
}
```

## **DELETE /tasks/{id}**

Delete a task.

Response: 204 NO CONTENT

## **POST /tasks/{id}/completed**

Mark a task as completed.

Response: 200 OK
```json
{
    "id": 1,
    "title": "My New Title",
    "description": "My New Description",
    "date": "2024-04-20",
    "time": "14:45",
    "completed": true
}
```

## **DELETE /tasks/completed**

Delete all completed tasks.

Response: 204 NO CONTENT
