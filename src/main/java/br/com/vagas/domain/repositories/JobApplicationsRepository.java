package br.com.vagas.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vagas.domain.*;

public interface JobApplicationsRepository extends JpaRepository<JobApplication, Long>{

    public List<JobApplication> findByOpportunity(Opportunity opportunity);
    
}
