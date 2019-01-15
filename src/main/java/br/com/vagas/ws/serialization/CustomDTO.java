package br.com.vagas.ws.serialization;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper=true)
public abstract class CustomDTO<W> extends ResourceSupport{

    
    @JsonProperty("id")
    Serializable customId;
    
    public void addCustomLinks(Class<?> serviceClass) {
        add(linkTo(serviceClass).slash(getCustomId()).withSelfRel());
        add(linkTo(serviceClass).withRel("all"));
    }
    
    public abstract void setWrapped(W wrapped) ;
    
    public abstract W getWrapped() ;
    
    public abstract void mapCustomId();

}
