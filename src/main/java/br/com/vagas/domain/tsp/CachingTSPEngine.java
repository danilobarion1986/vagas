package br.com.vagas.domain.tsp;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import lombok.*;


public class CachingTSPEngine implements TSPEngine {

    private TSPEngine underlying;

    private Map<Pair, Integer> cachingMap = new HashMap<>();
    
    public CachingTSPEngine(TSPEngine underlying) {
        this.underlying = Optional.ofNullable(underlying)
                .orElseThrow(() -> new IllegalArgumentException("Underlying engine cannot be null"));
        this.cachingMap = new ConcurrentHashMap<>();
    }
    
    @Override
    public int getSmallestDistanceBetweenLocations(String candidateLocation, String jobLocation)
            throws LocationsNotConnectedException {
        Pair p = new Pair(candidateLocation, jobLocation);
        
        return Optional.ofNullable(cachingMap.get(p))
                .orElseGet(() -> {
                    int cost = underlying.getSmallestDistanceBetweenLocations(candidateLocation, jobLocation);
                    cachingMap.put(p, cost);
                    return cost;
                });
        
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Pair {
        String a;
        String b;
    }
    

}
