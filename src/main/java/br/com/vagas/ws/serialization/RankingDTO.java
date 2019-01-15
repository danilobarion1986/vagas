package br.com.vagas.ws.serialization;

import com.fasterxml.jackson.annotation.*;

import br.com.vagas.domain.*;

public class RankingDTO {

    
    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private Person person;
    
    //Suppressed because it will be mapped to/from JSON
    @SuppressWarnings("unused")
    private int score;

    
    public RankingDTO(Ranking ranking) {
        this.person = ranking.getPerson();
        this.score = ranking.getScore();
    }
    
}
