package com.dov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dov.model.Answersbank;

@Repository
public interface AnswersbankRepository extends CrudRepository<Answersbank, Long> {

}
