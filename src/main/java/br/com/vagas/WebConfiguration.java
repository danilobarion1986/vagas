package br.com.vagas;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebConfiguration {

    
    @Bean
    public HttpMessageConverters customConverters() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        converter.setObjectMapper(objectMapper);
        
        return new HttpMessageConverters(Arrays.asList(converter));
        
    }

}
