package br.com.vagas.domain;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.*;
import org.mockito.*;

import br.com.vagas.domain.repositories.JobApplicationsRepository;
import br.com.vagas.domain.tsp.TSPEngine;

public class HRServiceTest {


    @Mock
    TSPEngine tspEngine;

    @Mock
    JobApplicationsRepository jobApplicationsRepository;

    @InjectMocks
    HRService service;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void calculateScoreTest() {

        assertEquals(100, calculateScore(100, 5, 5));
        assertEquals(87, calculateScore(100, 5, 4));
        assertEquals(75, calculateScore(75, 5, 4));
        assertEquals(62, calculateScore(50, 5, 4));
        assertEquals(50, calculateScore(100, 5, 1));
        assertEquals(50, calculateScore(100, 1, 5));
        assertEquals(0, calculateScore(0, 1, 5));
        assertEquals(12, calculateScore(25, 1, 5));
        assertEquals(12, calculateScore(25, 5, 1));

    }

    @Test
    public void calculateScoreByOpportunitySingleTest() {

        when(tspEngine.getSmallestDistanceBetweenLocations(eq("B"), eq("A"))).thenReturn(100);

        Opportunity opportunity = makeOpportunity("A", 5);

        when(jobApplicationsRepository.findByOpportunity(opportunity))
            .thenReturn(Arrays.asList(
                    makeJobApplication(opportunity, makePerson("B", 5),100)
        ));

        List<Ranking> rankings = service.listApplicationRankings(opportunity);
        assertNotNull(rankings);
        assertEquals(1, rankings.size());
        assertEquals(100, rankings.get(0).getScore());

    }


    @Test
    @Ignore
    public void calculateSeveralScoresByOpportunityTest() {

        when(tspEngine.getSmallestDistanceBetweenLocations(eq("B"), eq("A"))).thenReturn(100);
        when(tspEngine.getSmallestDistanceBetweenLocations(eq("C"), eq("A"))).thenReturn(50);
        when(tspEngine.getSmallestDistanceBetweenLocations(eq("F"), eq("A"))).thenReturn(0);

        Opportunity opportunity = makeOpportunity("A", 5);

        List<JobApplication> applications = Arrays.asList(
                makeJobApplication(opportunity, makePerson("B", 5),100),
                makeJobApplication(opportunity, makePerson("B", 1),50)
                );

        when(jobApplicationsRepository.findByOpportunity(opportunity)).thenReturn(applications);

        List<Ranking> rankings = service.listApplicationRankings(opportunity);
        assertNotNull(rankings);
        assertEquals(2, rankings.size());

        assertEquals(100, rankings.get(0).getScore());
        assertSame(applications.get(0).getPerson(), rankings.get(0).getPerson());

        assertEquals(50, rankings.get(1).getScore());
        assertSame(applications.get(1).getPerson(), rankings.get(2).getPerson());


    }


    private int calculateScore(int tspScore, int personLevel, int opportunityLevel) {
        when(tspEngine.getSmallestDistanceBetweenLocations(eq("A"), eq("B"))).thenReturn(tspScore);
        JobApplication application = makeJobApplication("A", personLevel, "B", opportunityLevel);
        when(jobApplicationsRepository.findByOpportunity(eq(application.getOpportunity()))).thenReturn(Arrays.asList(application));
        return service.listApplicationRankings(application.getOpportunity()).get(0).getScore();
    }


    private JobApplication makeJobApplication(Opportunity opportunity, Person person, int score) {
        JobApplication application = new JobApplication();

        application.setOpportunity(opportunity);
        application.setPerson(person);
        application.setScore(score);

        return application;
    }

    private JobApplication makeJobApplication(String personLocation, int personLevel, String opportunityLocation, int opportunityLevel) {
        JobApplication application = new JobApplication();

        Person person = makePerson(personLocation, personLevel);
        Opportunity opportunity = makeOpportunity(opportunityLocation, opportunityLevel);

        application.setOpportunity(opportunity);
        application.setPerson(person);

        return application;
    }

    private Opportunity makeOpportunity(String opportunityLocation, int opportunityLevel) {
        Opportunity opportunity = new Opportunity();
        opportunity.setLocation(opportunityLocation);
        opportunity.setLevel(opportunityLevel);
        return opportunity;
    }

    private Person makePerson(String personLocation, int personLevel) {
        Person person = new Person();
        person.setLocation(personLocation);
        person.setLevel(personLevel);
        return person;
    }

}
