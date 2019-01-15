package br.com.vagas.domain.tsp;

public interface TSPEngine {
    
    public int getSmallestDistanceBetweenLocations(String candidateLocation, String jobLocation);
    
    public static void validateInputs(String location, String destination) {
        if (location == null || destination == null) {
            throw new IllegalArgumentException("Both location and destination must be specified");
        }
    }

}
