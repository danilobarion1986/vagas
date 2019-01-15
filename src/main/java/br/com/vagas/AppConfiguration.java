package br.com.vagas;

import java.util.*;

import org.springframework.context.annotation.*;

import br.com.vagas.domain.tsp.*;
import br.com.vagas.domain.tsp.RangeOfValuesTSPEngine.Range;

@Configuration
public class AppConfiguration {

    
    @Bean
    public TSPEngine loadRangeMapTSPEngine() {
        
        Map<Range, Integer> rangeMap = new HashMap<>();
        
        rangeMap.put(new Range(0, 5)    , 100);
        rangeMap.put(new Range(6, 10)   , 75);
        rangeMap.put(new Range(11, 15)  , 50);
        rangeMap.put(new Range(16, 20)  , 25);
        rangeMap.put(new Range(21, null), 0);
        
        return new RangeOfValuesTSPEngine(loadUnderlyingEngine(), rangeMap);
    }
    
    
    
    private TSPEngine loadUnderlyingEngine() {
        
        Location A = new Location("A");
        Location B = new Location("B");
        Location C = new Location("C");
        Location D = new Location("D");
        Location E = new Location("E");
        Location F = new Location("F");
        
        A.connectTo(B, 5);
        B.connectTo(C, 7);
        B.connectTo(D, 3);
        C.connectTo(E, 4);
        D.connectTo(E, 10);
        D.connectTo(F, 8);
        
        Map<String, Location> locations = new HashMap<>();
        locations.put("A", A);
        locations.put("B", B);
        locations.put("C", C);
        locations.put("D", D);
        locations.put("E", E);
        locations.put("F", F);
        
        return new StandardTSPEngine(locations);
    }
    

}
