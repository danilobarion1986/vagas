package br.com.vagas.ws.serialization;

import com.fasterxml.jackson.annotation.*;

import br.com.vagas.domain.JobApplication;
import lombok.*;


@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO extends CustomDTO<JobApplication>{

    
    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private JobApplication wrapped;

    @Override
    public void mapCustomId() {
        if (wrapped != null) {
            this.customId = wrapped.getId();
        }
        else {
            this.customId = null;
        }
    }
    
}
