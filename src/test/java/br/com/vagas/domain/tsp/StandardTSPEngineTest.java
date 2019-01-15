package br.com.vagas.domain.tsp;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.*;

import lombok.SneakyThrows;


public class StandardTSPEngineTest {
    
    
    Location a = new Location("A");
    Location b = new Location("B");
    Location c = new Location("C");
    Location d = new Location("D");
    Location e = new Location("E");
    Location f = new Location("F");
    
    
    TSPEngine engineToTest;
    
    
    @Before
    public void setup() {
        a.connectTo(b, 5);
        b.connectTo(c, 7);
        b.connectTo(d, 3);
        c.connectTo(e, 4);
        d.connectTo(e, 10);
        d.connectTo(f, 8);
        
        Map<String, Location> locations = new HashMap<>();
        locations.put("A", a);
        locations.put("B", b);
        locations.put("C", c);
        locations.put("D", d);
        locations.put("E", e);
        locations.put("F", f);
        engineToTest = new StandardTSPEngine(locations);
        
    }
    
    @Test
    public void happyDayTests() {
        
        executeAssertion("A", "F", 16);
        executeAssertion("A", "E", 16);
        executeAssertion("F", "A", 16);
        executeAssertion("F", "C", 18);
        executeAssertion("E", "F", 18);
        executeAssertion("A", "B", 5);
    }
    
    
    @Test(timeout=30L)
    public void originAndDestinationAreTheSameTest() {
        executeAssertion("A", "A", 0);
    }
    
    
    @Test(expected=LocationsNotConnectedException.class)
    public void locationsAreDisconnected() {
        executeAssertion("A", "UNKNOWN", 0);
    }
    
    @Test
    public void leastCostPathIsNotTheShortestTest() {
        
        Location a = new Location("A");
        Location b = new Location("B");
        Location c = new Location("C");
        
        a.connectTo(b, 1);
        b.connectTo(c, 1);
        a.connectTo(c, 4);
        
        Map<String, Location> locations = new HashMap<>();
        locations.put("A", a);
        locations.put("B", b);
        locations.put("C", c);
        
        engineToTest = new StandardTSPEngine(locations);
        
        executeAssertion("A", "C", 2);
        
    }
    
    
    
    
    @SneakyThrows
    private void executeAssertion(String a, String b, int expectedCost) {
        assertEquals(expectedCost, engineToTest.getSmallestDistanceBetweenLocations(a, b));
    }
    
    
    
    
    
    
    
    
    

}
