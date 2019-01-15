package br.com.vagas.ws;

import static br.com.vagas.ws.Services.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import br.com.vagas.domain.*;
import br.com.vagas.ws.serialization.RankingDTO;

@RestController()
@RequestMapping(path={ API_VERSION + "/vagas"},
    consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RankingService {

    
    private HRService hrService;
    
    
    @GetMapping("/{id}/candidaturas/ranking")
    public List<RankingDTO> listRankings(@PathVariable("id")Long id) {
        
        Opportunity opportunity = hrService.findOpportunityById(id).orElseThrow(() -> new EntityNotFoundException());
        List<Ranking> rankings = hrService.calculateScores(opportunity);
        
        return rankings.stream()
                .map(r -> new RankingDTO(r))
                .collect(Collectors.toList());
        
    }
    

}
