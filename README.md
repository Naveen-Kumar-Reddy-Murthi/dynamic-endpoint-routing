# [Dynamically changing request mapping in spring boot]

[![Actions](https://github.com/gothinkster/spring-boot-realworld-example-app/workflows/Java%20CI/badge.svg)]

> ### Dynamic request mapping

# Brief
A sample application demonstrating dynamic request mapping

# Libraries used
1. Spring boot
2. H2 DB In-Memory
3. Lombok
4. Spring Data JPA
5. Maven for build and dependency Management

# How it works?
This application is designed to change a request mapping of a handler method
of a controller at runtime. Request mappings are maintained in h2 db, and a
cron job will keep polling for the new entries. The new entries from the db
are fetched and updated in a concurrent hashmap with key as handler method
name and value as the request mapping. In the controller handler method ,
itself incoming path variable will be matched to the entry from db for that
method matched. If they match, processing will continue. If not, Bad request
will be thrown. Please check Greetings controller code for more clarity.

# Database

It uses a H2 in memory database (for now), can be changed easily in the `application.properties` for any other database.
You can access the web console using following endpoint
`http://localhost:8080/h2`

# Getting started

You need Java 8 installed.

# How to test
Make sure you update the JWT token in the test file before running tests because
it for running tests authentication is required. Otherwise, tests will fail

mvn test

# How to run
mvn spring-boot:run

To test that it works, open a browser tab at .

    curl http://localhost.:8080/test?name=naveen

If you change the entry for this handler method, instantly after cron job,
new mapping will be activated. Older one will not work anymore.

# Help

Please fork and PR to improve the code.