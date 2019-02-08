package br.com.vagas.domain;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vagas.domain.repositories.*;
import br.com.vagas.domain.tsp.TSPEngine;

@Service
public class HRService {


    @Autowired
    private TSPEngine tspEngine;

    @Autowired
    private OpportunitiesRepository opportunitiesRepository;

    @Autowired
    private JobApplicationsRepository jobApplicationsRepository;

    @Autowired
    private PeopleRepository peopleRepository;


    public Person savePerson(Person person) {
        return peopleRepository.save(person);
    }

    public Opportunity saveOpportunity(Opportunity opportunity) {
        return opportunitiesRepository.save(opportunity);
    }

    public JobApplication saveJobApplication(JobApplication jobApplication) {
        int score = calculateScore(jobApplication);
        jobApplication.setScore(score);
        return jobApplicationsRepository.save(jobApplication);
    }

    public Optional<Opportunity> findOpportunityById(Long id) {
        return opportunitiesRepository.findById(id);
    }

    public Optional<Person> findPersonById(Long id) {
        return peopleRepository.findById(id);
    }

    public List<Opportunity> listOpportunities() {
        return opportunitiesRepository.findAll();
    }

    public List<Person> listPeople() {
        return peopleRepository.findAll();
    }

    public List<JobApplication> listJobApplications(Opportunity opportunity) {
        return jobApplicationsRepository.findByOpportunity(opportunity);
    }


    public List<Ranking> listApplicationRankings(Opportunity opportunity) {
        List<JobApplication> jobApplications = listJobApplications(opportunity);
        return jobApplications.stream()
            .map(application -> new Ranking(application.getPerson(), application.getScore()))
            .sorted((a,b) -> b.getScore() - a.getScore())
            .collect(Collectors.toList());
    }


    protected int calculateScore(JobApplication jobApplication) {

        int N = calculateN(jobApplication);
        int D = tspEngine
                .getSmallestDistanceBetweenLocations(
                        jobApplication.getPersonLocation(),
                        jobApplication.getOpportunityLocation());

        return (N + D) / 2;
    }


    private int calculateN(JobApplication jobApplication) {
        int NV = jobApplication.opportunityLevel();
        int NC = jobApplication.personLevel();

        int mod = Math.abs(NV - NC);
        return 100 - (25 * mod);

    }

}
