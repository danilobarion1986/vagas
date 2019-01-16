package br.com.vagas.ws.serialization;

import com.fasterxml.jackson.annotation.*;

import br.com.vagas.domain.*;
import br.com.vagas.ws.*;

public class RankingDTO {

    
    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private PersonDTO person;
    
    @JsonProperty("score")
    private int score;

    
    public RankingDTO(Ranking ranking) {
        this.person = new PersonDTO(ranking.getPerson());
        this.score = ranking.getScore();
    }
    
    public RankingDTO prepare() {
        this.person.mapCustomId();
        Services.wrap(this.person, PeopleService.class);
        return this;
    }
    
    
    
}
