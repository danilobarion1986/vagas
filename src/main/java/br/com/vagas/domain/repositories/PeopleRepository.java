package br.com.vagas.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vagas.domain.Person;

public interface PeopleRepository extends JpaRepository<Person, Long>{

}
