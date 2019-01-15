package br.com.vagas.ws;

import static br.com.vagas.ws.Services.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import br.com.vagas.domain.*;
import br.com.vagas.ws.serialization.OpportunityDTO;

@RestController()
@RequestMapping(path={ API_VERSION + "/vagas"},
    produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpportunitiesService {
    
    
    @Autowired
    private HRService hrService;
    
    @GetMapping()
    public List<OpportunityDTO> listOpportunities() {
        
        return hrService.listOpportunities()
                .stream()
                .map((o) -> wrap(new OpportunityDTO(o), OpportunitiesService.class))    
                .collect(Collectors.toList());
    }
    
    
    @GetMapping(path={"/{id}"})
    public OpportunityDTO findOpportunity(@PathVariable("id") Long id) {   
        Opportunity opportunity = hrService.findOpportunityById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        
        return wrap(new OpportunityDTO(opportunity), OpportunitiesService.class);
    }
    
    
    @PostMapping
    public HttpEntity<OpportunityDTO> createOpportunity(@RequestBody OpportunityDTO opportunity) {
        
        OpportunityDTO dto = wrap(new OpportunityDTO(hrService.saveOpportunity(opportunity.getWrapped())), OpportunitiesService.class);
        return ResponseEntity.created(linkTo(getClass()).slash(dto.getCustomId()).toUri()).body(dto);
        
    }

    

}
