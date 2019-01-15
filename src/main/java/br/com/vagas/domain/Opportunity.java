package br.com.vagas.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class Opportunity extends DomainEntity{


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
    
    @JsonProperty("empresa")
    String company;
    
    @JsonProperty("titulo")
    String title;
    
    @JsonProperty("descricao")
    String description;
    
    @JsonProperty("localizacao")
    String location;
    
    @JsonProperty("nivel")
    int level;

}
