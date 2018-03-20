package com.dov.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dov.model.Bookedexams;
import com.dov.model.Examinations;
import com.dov.model.User;

@Repository
public interface BookedexamsRepository extends CrudRepository<Bookedexams, Long> {
	public Optional<Bookedexams> findById(Long id);
	public List<Bookedexams> findByUser(User user);
	public Optional<Bookedexams> findByUserAndExaminations(User user, Examinations examinations);
}
