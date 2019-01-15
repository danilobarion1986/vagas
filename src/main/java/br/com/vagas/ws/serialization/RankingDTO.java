package br.com.vagas.ws.serialization;

import com.fasterxml.jackson.annotation.*;

import br.com.vagas.domain.*;

public class RankingDTO {

    
    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private PersonDTO person;
    
    @JsonProperty("score")
    private int score;

    
    public RankingDTO(Ranking ranking) {
        this.person = new PersonDTO(ranking.getPerson());
        this.person.mapCustomId();
        
        this.score = ranking.getScore();
    }
    
    
    
}
