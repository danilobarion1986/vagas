package br.com.vagas.domain.tsp;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.*;

public class CachingTSPEngineTest {

    
    @Mock
    TSPEngine underlyingEngine;
    
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    public void testDefaultCaching() {
        
        when(underlyingEngine.getSmallestDistanceBetweenLocations("A", "B")).thenReturn(10);
        CachingTSPEngine cachingTSPEngine = new CachingTSPEngine(underlyingEngine);
        
        cachingTSPEngine.getSmallestDistanceBetweenLocations("A", "B");
        cachingTSPEngine.getSmallestDistanceBetweenLocations("A", "B");
        
        verify(underlyingEngine, times(1)).getSmallestDistanceBetweenLocations(eq("A"), eq("B"));
        
    }
    
    
    @Test(expected=IllegalArgumentException.class)
    public void testEngineCannotBeBuiltWithoutUnderlying() {
        new CachingTSPEngine(null);
    }
    
    
    @Test
    public void testDifferentInvocationsOfCache() {
        
        when(underlyingEngine.getSmallestDistanceBetweenLocations("A", "B")).thenReturn(10);
        when(underlyingEngine.getSmallestDistanceBetweenLocations("B", "C")).thenReturn(5);
        when(underlyingEngine.getSmallestDistanceBetweenLocations("B", "A")).thenReturn(10);
        
        CachingTSPEngine cachingTSPEngine = new CachingTSPEngine(underlyingEngine);
        
        cachingTSPEngine.getSmallestDistanceBetweenLocations("A", "B");
        cachingTSPEngine.getSmallestDistanceBetweenLocations("A", "B");
        cachingTSPEngine.getSmallestDistanceBetweenLocations("B", "C");
        cachingTSPEngine.getSmallestDistanceBetweenLocations("B", "C");
        cachingTSPEngine.getSmallestDistanceBetweenLocations("B", "A");
        cachingTSPEngine.getSmallestDistanceBetweenLocations("B", "A");
        
        
        verify(underlyingEngine, times(1)).getSmallestDistanceBetweenLocations(eq("A"), eq("B"));
        verify(underlyingEngine, times(1)).getSmallestDistanceBetweenLocations(eq("B"), eq("C"));
        verify(underlyingEngine, times(1)).getSmallestDistanceBetweenLocations(eq("B"), eq("A"));
        
    }
    
    
    @Test(expected=LocationsNotConnectedException.class)
    public void testExceptionThrownIsRethrown() {
        
        when(underlyingEngine.getSmallestDistanceBetweenLocations("A", "B")).thenThrow(new LocationsNotConnectedException());
        CachingTSPEngine cachingTSPEngine = new CachingTSPEngine(underlyingEngine);
        cachingTSPEngine.getSmallestDistanceBetweenLocations("A", "B");
        
    }
    

}
