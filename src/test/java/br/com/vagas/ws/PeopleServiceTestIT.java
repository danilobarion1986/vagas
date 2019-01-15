package br.com.vagas.ws;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.vagas.domain.Person;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
            .header("Location", is(String.format("http://localhost:%d/v1/pessoas", port)))
            
            ;
            
        
    }
    

}
