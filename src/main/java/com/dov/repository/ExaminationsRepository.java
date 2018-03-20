package com.dov.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dov.model.Examinations;
import com.dov.model.Institution;

@Repository
public interface ExaminationsRepository extends CrudRepository<Examinations, Long> {
	public List<Examinations> findByInstitution(Institution institution);
	public Optional<Examinations> findByInstitutionAndExamcode(Institution institution, String examcode);
}
