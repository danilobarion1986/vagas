package br.com.vagas.ws;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.*;

import br.com.vagas.VagasApplication;
import io.restassured.RestAssured;
import lombok.SneakyThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes=VagasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PeopleServiceTestIT {
    
    
    @LocalServerPort
    int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }
    
    @Test
    @SneakyThrows
    public void testBasicInsert() {
        
        
        given().body(new ClassPathResource("/person.json").getFile())
            .header("Content-Type", "application/json")
            .post("/v1/pessoas")
            .then()
            .statusCode(201)
            .and()
            .header("Location", is(String.format("http://localhost:%d/v1/pessoas/1", port)))
            .and()
            .body("nome", is("John Doe"))
            .body("profissao", is("Engenheiro de Software"))
            .body("localizacao", is("C"))
            .body("nivel", is(2))
            .body("id", is(1))
            .body("_links.size()", greaterThanOrEqualTo(2))
            ;
            
        given().get("/v1/pessoas/1")
        .then()
        .statusCode(200)
        .and()
        .body("nome", is("John Doe"))
        .body("profissao", is("Engenheiro de Software"))
        .body("localizacao", is("C"))
        .body("nivel", is(2))
        .body("id", is(1))
        ;
        
    }
    

}
