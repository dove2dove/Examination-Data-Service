package com.dov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dov.model.Questions;

@Repository
public interface QuestionsRepository extends CrudRepository<Questions, Long> {

}
