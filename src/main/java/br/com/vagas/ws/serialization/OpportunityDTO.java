package br.com.vagas.ws.serialization;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.*;

import br.com.vagas.domain.Opportunity;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class OpportunityDTO extends CustomDTO<Opportunity>{

    
    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    @Valid
    private Opportunity wrapped;

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
