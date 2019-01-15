package br.com.vagas.ws;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.vagas.VagasApplication;
import io.restassured.RestAssured;
import lombok.SneakyThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes=VagasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobApplicationsServiceTestIT {

    
    
    @LocalServerPort
    int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }
    
    
    @Test
    @SneakyThrows
    public void testBasicInsert() {
        
        given()
        .body(new ClassPathResource("/opportunity.json").getFile())
        .header("Content-Type", "application/json")
        .post("/v1/vagas")
        .then()
        .statusCode(201);
        
        given().body(new ClassPathResource("/person.json").getFile())
        .header("Content-Type", "application/json")
        .post("/v1/pessoas")
        .then()
        .statusCode(201);
        
        given()
            .body(new ClassPathResource("/jobApplication.json").getFile())
            .header("Content-Type", "application/json")
            .post("/v1/candidaturas")
            .then()
            .statusCode(201)        
            .header("Location", is(String.format("http://localhost:%d/v1/candidaturas/1", port)));
    }
    
    @Test()
    @SneakyThrows
    public void testRanking() {
        
        given().body(new ClassPathResource("/maryJane.json").getFile())
        .header("Content-Type", "application/json")
        .post("/v1/pessoas")
        .then()
        .statusCode(201);
        
        
        given()
        .get("/v1/vagas/1/candidaturas/ranking")
        .then()
        .statusCode(200)
        .and()
        .body("nome[0]", is("Mary Jane"))
        .body("profissao[0]", is("Engenheira de Software"))
        .body("localizacao[0]", is("A"))
        .body("nivel[0]", is(3))
        .body("score[0]", is(100))
        .body("nome[1]", is("John Doe"))
        .body("profissao[1]", is("Engenheiro de Software"))
        .body("localizacao[1]", is("C"))
        .body("nivel[1]", is(2))
        .body("score[1]", is(62))
        ;
        
    }

}
