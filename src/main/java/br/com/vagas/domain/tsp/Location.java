package br.com.vagas.domain.tsp;

import java.util.*;

import lombok.*;

@Data
@EqualsAndHashCode(of="label")
@ToString(of= {"label"})
public class Location {

    private Map<Location, Integer> nextLocations = new HashMap<>();
    
    @NonNull
    private String label;
    
    public void connectTo(Location l, Integer cost) {
        nextLocations.put(l, cost);
        l.nextLocations.put(this, cost); //The cost of travelling from /to each location is the same
    }
    
    public int getCostOf(Location l) throws LocationsNotConnectedException {
        return Optional.ofNullable(nextLocations.get(l))
                .orElseThrow(() -> new LocationsNotConnectedException(String.format("Location %s is not connected to %s", this, l)));
    }
    
    public Set<Location> getCloseLocations() {
        return nextLocations.keySet();
    }

}
