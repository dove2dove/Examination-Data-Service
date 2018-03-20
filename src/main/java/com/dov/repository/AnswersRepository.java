package com.dov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dov.model.Answers;

@Repository
public interface AnswersRepository extends CrudRepository<Answers, Long> {

}
