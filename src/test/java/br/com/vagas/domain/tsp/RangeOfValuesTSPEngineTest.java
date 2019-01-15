package br.com.vagas.domain.tsp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.*;
import org.mockito.*;

import br.com.vagas.domain.tsp.RangeOfValuesTSPEngine.Range;

public class RangeOfValuesTSPEngineTest {

    @Mock
    TSPEngine underlying;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testRanges() {
        
        Map<Range, Integer> ranges = new HashMap<>();
        ranges.put(new Range(0, 10), 0);
        ranges.put(new Range(11, 20), 5);
        ranges.put(new Range(21, null), 10);
        
        when(underlying.getSmallestDistanceBetweenLocations(eq("A"), eq("B"))).thenReturn(0);
        when(underlying.getSmallestDistanceBetweenLocations(eq("C"), eq("D"))).thenReturn(1);
        when(underlying.getSmallestDistanceBetweenLocations(eq("E"), eq("F"))).thenReturn(10);
        when(underlying.getSmallestDistanceBetweenLocations(eq("G"), eq("H"))).thenReturn(11);
        when(underlying.getSmallestDistanceBetweenLocations(eq("I"), eq("J"))).thenReturn(20);
        when(underlying.getSmallestDistanceBetweenLocations(eq("K"), eq("L"))).thenReturn(21);
        when(underlying.getSmallestDistanceBetweenLocations(eq("M"), eq("N"))).thenReturn(30);
        
        
        RangeOfValuesTSPEngine  engine = new RangeOfValuesTSPEngine(underlying, ranges);
        
        //assertEquals(0, engine.get);
        assertEquals(0, engine.getSmallestDistanceBetweenLocations("A", "B"));
        assertEquals(0, engine.getSmallestDistanceBetweenLocations("C", "D"));
        assertEquals(0, engine.getSmallestDistanceBetweenLocations("E", "F"));
        assertEquals(5, engine.getSmallestDistanceBetweenLocations("G", "H"));
        assertEquals(5, engine.getSmallestDistanceBetweenLocations("I", "J"));
        assertEquals(10, engine.getSmallestDistanceBetweenLocations("K", "L"));
        assertEquals(10, engine.getSmallestDistanceBetweenLocations("M", "N"));
        
        
    }
    

}
