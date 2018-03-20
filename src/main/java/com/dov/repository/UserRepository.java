package com.dov.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dov.model.Institution;
import com.dov.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public  List<User> findByUsername(String userName);
	public Optional<User>  findByInstitutionAndUsername(Institution institution, String userName);
}
