package br.com.vagas.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

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
    @NotNull
    String company;
    
    @JsonProperty("titulo")
    @NotNull
    String title;
    
    @JsonProperty("descricao")
    @NotNull
    String description;
    
    @JsonProperty("localizacao")
    @NotNull
    String location;
    
    @JsonProperty("nivel")
    @Min(1)
    @Max(5)
    int level;

}
