package br.com.vagas.domain.tsp;

import java.util.*;

import lombok.*;

/**
 * Simplest possible implementation of TSP (Travelling Salesman Problem).
 * 
 * I know there are several ways of solving the problem. But I took this
 * approach because:
 * 
 * 1) Simplicity's sake: it works just like the way I would if I were to solve
 *    it by eye. (aka KISS principle) 
 * 
 * 2) MVP: The initial set is known, and it is small enough to work this way. I mean, 
 *    we should do it as simple as we can to generate some value first and refactor only if needed.
 * 
 * @author Alexandre Saudate
 *
 */
@AllArgsConstructor
public class StandardTSPEngine implements TSPEngine {
    
    
    @NonNull
    private Map<String, Location> locations;
    
    
    @Override
    public int getSmallestDistanceBetweenLocations(String candidateLocation, String jobLocation)
            throws LocationsNotConnectedException {
        TSPEngine.validateInputs(candidateLocation, jobLocation);
        candidateLocation = candidateLocation.trim();
        jobLocation = jobLocation.trim();
        
        Location origin = locations.get(candidateLocation);
        Location destination = locations.get(jobLocation);
        
        Path path = getPathBetweenLocations(origin, destination);
        
        return path.calculateCost();
    }

    private Path getPathBetweenLocations(Location origin, Location destination) throws LocationsNotConnectedException {

        Path initialPath = new Path();
        if (origin.equals(destination)) {
            return initialPath;
        }

        initialPath.addLocation(origin);
        List<Path> possiblePaths = getPossibleSubPaths(origin, destination, initialPath, new HashSet<>());
        int minimumCost = Integer.MAX_VALUE;
        Path chosenPath = null;

        if (possiblePaths.isEmpty()) {
            throw new LocationsNotConnectedException(
                    String.format("No path could be found between %s and %s", origin, destination));
        }

        for (Path p : possiblePaths) {
            int cost = p.calculateCost();
            if (cost < minimumCost) {
                chosenPath = p;
                minimumCost = cost;
            }
        }

        return chosenPath;
    }

    private List<Path> getPossibleSubPaths(Location origin, Location destination, Path initialPath,
            Set<Location> loopPreventionSet) {

        if (origin.equals(destination)) {
            Path p = new Path();
            p.addLocation(origin);
            return Arrays.asList(p);
        }

        List<Path> paths = new ArrayList<>();
        Set<Location> loopPreventionSetCopy = new HashSet<>(loopPreventionSet);
        for (Location point : origin.getCloseLocations()) {
            if (!loopPreventionSet.contains(point)) {
                loopPreventionSetCopy.add(origin);
                Path p = new Path();
                p.addLocation(point);
                List<Path> newPaths = getPossibleSubPaths(point, destination, p, loopPreventionSetCopy);

                for (Path newPath : newPaths) {
                    paths.add(initialPath.append(newPath));
                }

            }
        }
        return paths;
    }

}
