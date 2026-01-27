package project.application.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource; 
import org.testcontainers.containers.MySQLContainer; 
import org.testcontainers.junit.jupiter.Container; 
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@EntityScan(basePackages = "project.application.entities") 
public class AbstractsIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.32")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");
            

    @DynamicPropertySource
    static void configurationDataSource(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void deveSalvarUsuario(){

        given()
            .contentType("application/json")
            .body("""
                {
                  "name": "Maria",
                  "email": "maria@email.com"
                }
            """)
        .when()
            .post("/customers")
        .then()
            .statusCode(201)
            .body("name", equalTo("Maria"))
            .body("email", equalTo("maria@email.com"));
    }
}
