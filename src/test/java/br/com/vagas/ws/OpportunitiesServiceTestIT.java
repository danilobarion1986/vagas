package br.com.vagas.ws;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.*;
import org.junit.runner.RunWith;
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
public class OpportunitiesServiceTestIT {

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
        .statusCode(201)
        .and()
        .header("Location", is(String.format("http://localhost:%d/v1/vagas/1", port)))
        .and()
        .body("empresa", is("Teste"))
        .body("titulo", is("Vaga teste"))
        .body("descricao", is("Criar os mais diferentes tipos de teste"))
        .body("localizacao", is("A"))
        .body("nivel", is(3))
        .body("id", is(1))
        .body("_links.size()", greaterThanOrEqualTo(2))
        ;
        
        given().get("/v1/vagas/1")
        .then()
        .statusCode(200)
        .and()
        .body("empresa", is("Teste"))
        .body("titulo", is("Vaga teste"))
        .body("descricao", is("Criar os mais diferentes tipos de teste"))
        .body("localizacao", is("A"))
        .body("nivel", is(3))
        .body("id", is(1))
        ;
        
    }
    
    

}
