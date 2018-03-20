package com.dov.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dov.model.Institution;
import com.dov.model.User;
import com.dov.repository.InstitutionRepository;
import com.dov.repository.UserRepository;
import com.dov.services.UserService;

@RestController
@RequestMapping("/{instcode}/user")
public class UserRestController {

	private static final Logger logger = Logger.getLogger(UserRestController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getUserList(@PathVariable("instcode") String instCode) {
		return userService.listByInstitutionCode(instCode);
	}

	//
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public User getUser(@PathVariable("instcode") String instCode, @PathVariable("username") String userName) {
		User user = new User();
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		if (institution.isPresent()) {
			Optional<User> optuser = userRepository.findByInstitutionAndUsername(institution.get(), userName);
			if (optuser.isPresent()) {
				user = optuser.get();
				logger.info("User found and returned!!!");
			} else {
				logger.info("User record not found!!!");
			}
		} else {
			logger.info("Wrong Institution Code!!!");
		}
		return user;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<User> updateUser(@PathVariable("instcode") String instCode, @Valid @RequestBody User vUser) {
		User returnUser = new User();
		if (vUser != null) {
			if (vUser.getInstitution().getInstcode().equals(instCode)) {
				if (vUser.getId() != null) {
					logger.info("User ID for this process is " + vUser.getId());
					User newUser = userService.listByInstitutionCodeAndUserName(instCode, vUser.getUsername());
					if ((newUser != null) && (newUser.getId() != null) && (newUser.getInstitution().getInstcode().equals(vUser.getInstitution().getInstcode())) && (newUser.getId().equals(vUser.getId()))) {
						returnUser = userRepository.save(vUser);
						logger.info("User record updated!!!");
						return new ResponseEntity<User>(returnUser, HttpStatus.OK);
					} else {
						logger.info("Error with User record!!!");
						return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
					}
				} else {
					logger.info("Duplicate record!!, record not updated");
					return new ResponseEntity<User>(new User(), HttpStatus.CONFLICT);
				}
			} else {
				logger.info("Invalid user or Institution records!!, record not updated");
				return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Invalid user record!!, record not updated");
			return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public User createUser(@PathVariable("instcode") String instCode, @Valid @RequestBody User vUser) {
		User returnUser = new User();
		if (vUser != null) {
			if (vUser.getInstitution().getInstcode().equals(instCode)) {
				if (vUser.getId() != null) {
					logger.info("Error with User record!!!");
				} else {
					User newUser = userService.listByInstitutionCodeAndUserName(instCode, vUser.getUsername());
					if (newUser.getId() == null) {
						if (vUser.getUsername().trim().length() >= 6) {
							returnUser = userRepository.save(vUser);
							logger.info("User record added!!!");
						} else {
							logger.info("Error with UserName!!!");
						}
					} else {
						logger.info("Duplicate record!!, record not updated");
					}
				}
			} else {
				logger.info("Invalid user or Institution records!!, record not updated");
			}
		} else {
			logger.info("Invalid user record!!, record not updated");
		}
		return returnUser;
	}

	@RequestMapping(value = "/delete/{username}", method = RequestMethod.GET)
	public ResponseEntity<User> deleteUser(@PathVariable("instcode") String instCode, @PathVariable("username") String userName) {
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		User newUser = userService.listByInstitutionCodeAndUserName(instCode, userName);
		if ((newUser.getId() != null) && (institution.isPresent())) {
			institution.get().removeUser(newUser);
			institutionRepository.save(institution.get());
			logger.info("User record deleted!!!");
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		} else {
			logger.info("User record not found, Not Deleted !!!");
			return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
		}
	}
}
