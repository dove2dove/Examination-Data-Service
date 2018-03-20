package com.dov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dov.model.Questionsbank;

@Repository
public interface QuestionsbankRepository extends CrudRepository<Questionsbank, Long> {

}
