package br.com.vagas.ws;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.http.ResponseEntity;

import br.com.vagas.ws.serialization.CustomDTO;

public class Services {

    public static final String API_VERSION = "v1";
    
    public static <T extends CustomDTO<?>> T wrap(T dto, Class<?> serviceClass) {
        
        dto.mapCustomId();
        dto.addCustomLinks(serviceClass);
        
        return dto;
    }
    
    protected static <T extends CustomDTO<?>> ResponseEntity<T> created(T dto, Class<?> serviceClass) {
        dto = wrap(dto, serviceClass);
        return ResponseEntity
                .created(linkTo(serviceClass).slash(dto.getCustomId()).toUri())
                .body(dto);
    }

}
