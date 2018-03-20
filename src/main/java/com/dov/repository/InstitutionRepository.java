package com.dov.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dov.model.Institution;

@Repository
public interface InstitutionRepository extends CrudRepository<Institution, Long> {
	public Optional<Institution> findByInstcode(String instcode);
}
