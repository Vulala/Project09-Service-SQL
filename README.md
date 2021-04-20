# Service-SQL

**REST API** used to communicate with a Relational Database (**SQL**) via **HTTP** request. <br>
In such, the application provides differents endpoints related to **CRUD** operations.

For more information about the available endpoints, feel free to checkout the swagger's documentation.


## Stack

- **Java 15** *(should run smoothly on older versions)*
- **Spring Boot 2.4.4**
- **Spring Boot Data JPA**
- **Spring Boot AOP 2.4.4**
- **Spring Boot Test**
- **JaCoCo 0.8.6**
- **Swagger 3.0.0**
- **H2 Database**
- **PostgreSQL**
- **Gradle 6.8.3**



## Installation

The application is quite easy to install; it only needs to be imported in your preferred IDE as a gradle project. <br>
Then it needs a relational database which can handle the UIID type. Otherwise, you might be forced to change that one by something else like an ID int. <br>
Then you can run the SQL script provided under **src/main/resources**; it creates the schema and populates the database with a few data. (*for testing purpose*) <br>


**Listen on port: 8081**


## Test

The application is entirely tested and reach a **90%** + code coverage. <br>
You can do end-to-end tests by doing HTTP request with the differents endpoints provided, f.e with Postman. <br>
If you wish to add or tweak some tests, you can find them under the traditional **src/test/java** package. <br>

Note that the H2 database make use of the provided scripts, *data.slq* and *schema.sql*, these 2 are mandatory for the H2 database to work properly. <br>
The *schema.sql* script is made mandatory because the patient model define the schema it must use. <br>


## Logs

The application is logged by making use of the **Aspect Oriented Programming**. <br>
*LoggingAspect* is the class using **AOP** which define the logging. <br>


## Documentation

The application uses Swagger2 to build up the documentation. <br>
To access the documentation, run the application and reach those links: <br>
[UI](http://localhost:8081/swagger-ui/) | [JSON](http://localhost:8081/v2/api-docs)



