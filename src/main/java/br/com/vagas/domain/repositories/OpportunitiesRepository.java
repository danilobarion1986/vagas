package br.com.vagas.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vagas.domain.Opportunity;

public interface OpportunitiesRepository extends JpaRepository<Opportunity, Long>{

}
