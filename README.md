# Browser Events REST API

REST API exposing endpoints to perform CRUD operations on browser events

## Docs

To access the interactive documentation of the API go to [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

## Docker

### Docker Compose
This option quickly creates all the containers needed to launch the application. In particular, it creates two containers, one for the API and the other for the MongoDB database.

```shell
docker-compose up -d
```

### Build and run only the container of the API
This option is recommended if you want to use an existing MongoDB database.

* __Building Docker Image__: build the image starting from the [Dockerfile](Dockerfile)
```shell
docker build --tag browser-events-api:latest .
```
* __Running Docker Image__: runs the image in daemon mode. The API will be accessible on port 8080.
```shell
docker run -i -t --name browser-events-api -p 8080:8080 -d browser-events-api:latest
```