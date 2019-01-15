package br.com.vagas.ws;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.*;

import br.com.vagas.VagasApplication;
import br.com.vagas.domain.Person;
import io.restassured.RestAssured;

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
    public void testBasicInsert() {
        
        Person person = new Person();
        person.setLevel(5);
        person.setLocation("A");
        person.setName("Alexandre Saudate");
        person.setProfession("IT Specialist");
        
        given().body(person)
            .header("Content-Type", "application/json")
            .post("/v1/pessoas")
            .then()
            .statusCode(201)
            .and()
            .header("Location", is(String.format("http://localhost:%d/v1/pessoas/1", port)))
            .and()
            .body("nome", is("Alexandre Saudate"))
            .body("profissao", is("IT Specialist"))
            .body("localizacao", is("A"))
            .body("nivel", is(5))
            .body("id", is(1))
            ;
            
        
    }
    

}
