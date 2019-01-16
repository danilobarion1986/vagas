package br.com.vagas.ws;

import static br.com.vagas.ws.Services.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import br.com.vagas.domain.*;
import br.com.vagas.ws.serialization.RankingDTO;

@RestController()
@RequestMapping(path={ API_VERSION + "/vagas"},
    produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RankingService {

    
    @Autowired
    private HRService hrService;
    
    
    @GetMapping("/{id}/candidaturas/ranking")
    public List<RankingDTO> listRankings(@PathVariable("id")Long id) {
        
        Opportunity opportunity = hrService.findOpportunityById(id).orElseThrow(() -> new EntityNotFoundException());
        List<Ranking> rankings = hrService.calculateScores(opportunity);
        
        return rankings.stream()
                .map(r -> new RankingDTO(r))
                .map(r -> r.prepare())
                .collect(Collectors.toList());
        
    }
    

}
