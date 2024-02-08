[![Build Status](https://ci-cd.springdoc.org:8443/buildStatus/icon?job=springdoc-demos%2F2.x)](https://ci-cd.springdoc.org:8443/job/springdoc-demos/job/2.x/)

# To-Do App Server

## **POST /tasks**
Request
```json
{
    "title": "My Title",
    "description": "My Description,
    "date": "2024-02-20",
    "time": "10:45"
}
```
Response: 201 Created
```json
{
    "id": 1,
    "title": "My Title",
    "description": "My Description,
    "date": "2024-02-20",
    "time": "10:45",
    "completed": false
}
```

## **GET /tasks**
Response: 200 OK
```json
[{
    "id": 1,
    "title": "My Title",
    "description": "My Description,
    "date": "2024-02-20",
    "time": "10:45",
    "completed": false
}]
```

## **GET /task/{id}**
Response: 200 OK
```json
{
    "id": 1,
    "title": "My Title",
    "description": "My Description,
    "date": "2024-02-20",
    "time": "10:45",
    "completed": false
}
```

## **PATCH /task/{id}**
Request
```json
{
    "title": "My New Title",
    "description": "My New Description,
    "date": "2024-04-20",
    "time": "14:45"
}
```
Response: 200 OK
```json
{
    "id": 1,
    "title": "My New Title",
    "description": "My New Description,
    "date": "2024-04-20",
    "time": "14:45",
    "completed": false
}
```

## **DELETE /task/{id}**
Response: 204 NO CONTENT

## **POST /task/{id}/completed**
Response: 200 OK
```json
{
    "id": 1,
    "title": "My New Title",
    "description": "My New Description,
    "date": "2024-04-20",
    "time": "14:45",
    "completed": true
}
```