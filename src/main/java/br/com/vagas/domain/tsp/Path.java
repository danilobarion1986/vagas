package br.com.vagas.domain.tsp;

import java.util.*;

import lombok.*;

@Getter
@ToString()
public class Path {

    private List<Location> locations = new ArrayList<>();
    
    public static Path of(Location a, Location b) {
        Path p = new Path();
        p.locations.addAll(Arrays.asList(a,b));
        return p;
    }

    public void addLocation(Location l) {
        this.locations.add(l);
    }

    public Path append(Path path) {
        Path newPath = new Path();
        newPath.locations = new ArrayList<>(this.locations);
        newPath.locations.addAll(path.locations);
        return newPath;
    }

    public int calculateCost() throws LocationsNotConnectedException {
        int cost = 0;
        for (int i = 1; i < this.locations.size(); i++) {
            Location a = this.locations.get(i - 1);
            Location b = this.locations.get(i);
            cost += a.getCostOf(b);
        }
        return cost;
    }

}
