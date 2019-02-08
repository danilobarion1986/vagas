package br.com.vagas.domain;

import javax.persistence.*;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class JobApplication extends DomainEntity{


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Person person;

    @ManyToOne
    Opportunity opportunity;

    Integer score;

    public int opportunityLevel() {
        return opportunity.getLevel();
    }

    public int personLevel() {
        return person.getLevel();
    }

    public String getOpportunityLocation() {
        return opportunity.getLocation();
    }

    public String getPersonLocation() {
        return person.getLocation();
    }

}
