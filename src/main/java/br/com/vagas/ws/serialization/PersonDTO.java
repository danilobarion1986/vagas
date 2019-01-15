package br.com.vagas.ws.serialization;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.*;

import br.com.vagas.domain.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class PersonDTO extends CustomDTO<Person>{
    

    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    @Valid
    private Person wrapped;
    
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
