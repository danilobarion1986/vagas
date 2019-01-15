package br.com.vagas.ws;

import static br.com.vagas.ws.Services.API_VERSION;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import br.com.vagas.domain.*;
import br.com.vagas.ws.serialization.PersonDTO;

@RestController()
@RequestMapping(path={ API_VERSION + "/pessoas"},
    consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PeopleService {
    
    
    @Autowired
    private HRService hrService;
    
    
    @PostMapping
    public HttpEntity<PersonDTO> createJobApplication(@Valid @RequestBody PersonDTO dto) {
        
        Person saved = hrService.savePerson(dto.getWrapped());
        return Services.created(new PersonDTO(saved), getClass());
        
    } 
    
    
    @GetMapping
    public List<PersonDTO> listAllPeople() {
        
        List<Person> people = hrService.listPeople();
        return people.stream().map(p -> new PersonDTO(p))
                .map(p -> Services.wrap(p, getClass()))
                .collect(Collectors.toList());
        
    }
    
    
    @GetMapping("/{id}")
    public PersonDTO findPerson(@PathVariable("id" )Long id) {
        
        Optional<Person> optionalPerson = hrService.findPersonById(id);
        Person person = optionalPerson.orElseThrow(() -> new EntityNotFoundException());
        
        return Services.wrap(new PersonDTO(person), getClass());
    }

}
