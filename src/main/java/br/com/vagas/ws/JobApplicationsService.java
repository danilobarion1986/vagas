package br.com.vagas.ws;

import static br.com.vagas.ws.Services.API_VERSION;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import br.com.vagas.domain.*;
import br.com.vagas.ws.serialization.JobApplicationDTO;

@RestController()
@RequestMapping(path={ API_VERSION + "/candidaturas"},
    produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JobApplicationsService {

    
    @Autowired
    private HRService hrService;
    
    
    @PostMapping
    public HttpEntity<JobApplicationDTO> createJobApplication(@RequestBody @Valid JobApplicationDTO dto) {
        
        Long idOpportunity = dto.getIdOpportunity();
        Long idPerson = dto.getIdPerson();
        
        Optional<Opportunity> optionalOpportunity = hrService.findOpportunityById(idOpportunity);
        Optional<Person> optionalPerson = hrService.findPersonById(idPerson);
        
        JobApplication jobApplication = new JobApplication();
        jobApplication.setPerson(optionalPerson.orElseThrow(() -> new EntityNotFoundException()));
        jobApplication.setOpportunity(optionalOpportunity.orElseThrow(() -> new EntityNotFoundException()));
        
        jobApplication = hrService.saveJobApplication(jobApplication);
        dto = new JobApplicationDTO(jobApplication);
        
        return Services.created(dto, getClass());
        
    }        
    
    @GetMapping("/vagas/{id}")
    public List<JobApplicationDTO> listJobApplications(@PathVariable("id") Long opportunityId) {
        
        Optional<Opportunity> optionalOpportunity = hrService.findOpportunityById(opportunityId);
        Opportunity opportunity = optionalOpportunity.orElseThrow(() -> new EntityNotFoundException());
        
        List<JobApplication> applications = hrService.listJobApplications(opportunity);
        
        return applications.stream().map((a) -> new JobApplicationDTO(a)).collect(Collectors.toList());
        
    }
    
}
