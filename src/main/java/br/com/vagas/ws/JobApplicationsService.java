package br.com.vagas.ws;

import static br.com.vagas.ws.Services.API_VERSION;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import br.com.vagas.domain.*;
import br.com.vagas.ws.serialization.JobApplicationDTO;

@RestController()
@RequestMapping(path={ API_VERSION + "/candidaturas"},
    consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JobApplicationsService {

    
    @Autowired
    private HRService hrService;
    
    
    @PostMapping
    public HttpEntity<JobApplicationDTO> createJobApplication(@RequestBody JobApplicationDTO dto) {
        
        JobApplication application = hrService.saveJobApplication(dto.getWrapped());
        
        return Services.created(new JobApplicationDTO(application), getClass());
        
    }        
    
    @GetMapping("/vagas/{id}")
    public List<JobApplicationDTO> listJobApplications(@PathVariable("id") Long opportunityId) {
        
        Optional<Opportunity> optionalOpportunity = hrService.findOpportunityById(opportunityId);
        Opportunity opportunity = optionalOpportunity.orElseThrow(() -> new EntityNotFoundException());
        
        List<JobApplication> applications = hrService.listJobApplications(opportunity);
        
        return applications.stream().map((a) -> new JobApplicationDTO(a)).collect(Collectors.toList());
        
    }
    
}
