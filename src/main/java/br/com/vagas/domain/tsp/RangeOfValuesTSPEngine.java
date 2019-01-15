package br.com.vagas.domain.tsp;

import java.util.*;

import lombok.EqualsAndHashCode;

public class RangeOfValuesTSPEngine implements TSPEngine {
    
    private TSPEngine underlyingEngine;
    private Map<Range, Integer> valueRanges;

    public RangeOfValuesTSPEngine(TSPEngine engine, Map<Range, Integer> valueRanges) {
        this.underlyingEngine = engine;
        this.valueRanges = valueRanges;
    }

    @Override
    public int getSmallestDistanceBetweenLocations(String candidateLocation, String jobLocation) {
        
        int score = underlyingEngine.getSmallestDistanceBetweenLocations(candidateLocation, jobLocation);
        for (Map.Entry<Range, Integer> entry : valueRanges.entrySet()) {
            Range range = entry.getKey();
            if (range.match(score)) {
                return entry.getValue();
            }
        }

        throw new RuntimeException("Did not match any locations");
    }
    
    
    @EqualsAndHashCode
    public static class Range {
        
        public int lowerBound;
        public Integer upperBound;
        
        public Range(int lowerBound, Integer upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }
        
        public boolean match(int score) {
            return score >= lowerBound 
                    && score <= Optional.ofNullable(upperBound).orElse(Integer.MAX_VALUE).intValue();
        }
        
        
    }

}
