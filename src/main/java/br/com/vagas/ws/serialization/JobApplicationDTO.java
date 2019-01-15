package br.com.vagas.ws.serialization;

import com.fasterxml.jackson.annotation.*;

import br.com.vagas.domain.JobApplication;
import lombok.*;


@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO extends CustomDTO<JobApplication>{

    
    @JsonIgnore
    private JobApplication wrapped;
    
    
    @JsonProperty("id_vaga")
    private Long idOpportunity;
    
    @JsonProperty("id_pessoa")
    private Long idPerson;
    
    public JobApplicationDTO(JobApplication jobApplication) {
        setWrapped(jobApplication);
    }

    @Override
    public void mapCustomId() {
        if (wrapped != null) {
            this.customId = wrapped.getId();
        }
        else {
            this.customId = null;
        }
    }
    
    public void setWrapped(JobApplication jobApplication) {
        this.wrapped = jobApplication;
        if (jobApplication != null && jobApplication.getOpportunity() != null) {
            this.idOpportunity = jobApplication.getOpportunity().getId();
        }
        else {
            this.idOpportunity = null;
        }
        if (jobApplication != null && jobApplication.getPerson() != null) {
        
            this.idPerson = jobApplication.getPerson().getId();
        }
        else {
            this.idPerson = null;
        }
    }
    
}
