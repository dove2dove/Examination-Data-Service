package com.dov.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dov.model.Institution;
import com.dov.model.User;
import com.dov.repository.InstitutionRepository;

@Service
public class UserService {
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	public List<User> listByInstitutionCode(String instCode){
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		List<User> userList = new ArrayList<>();
		if (institution.isPresent()) {
			List<User> setList = institution.get().getUserList();
			userList = new ArrayList<>(setList);
		}
		return userList; 
	}
	
	public User listByInstitutionCodeAndUserName(String instCode, String userName){
		List<User> userList = listByInstitutionCode(instCode);
		User user = new User();
		if (!userList.isEmpty()) {
			Optional<User> newUser = userList.stream().filter(s -> s.getUsername().equals(userName)).findFirst();
			if (newUser.isPresent()){
			user = newUser.get();
			}
		}
		return user;
	}
	
}
