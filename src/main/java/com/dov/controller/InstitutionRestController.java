package com.dov.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dov.model.Institution;
import com.dov.repository.InstitutionRepository;

@RestController
@RequestMapping("/institution")
public class InstitutionRestController {
	private static final Logger logger = Logger.getLogger(InstitutionRestController.class);

	@Autowired
	private InstitutionRepository institutionRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Institution> getInstitutionList() {
		Iterable<Institution> institutionList = institutionRepository.findAll();
		logger.info("Institution List Returned Successfully");
		return StreamSupport.stream(institutionList.spliterator(), false).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{instcode}", method = RequestMethod.GET)
	public Institution getInstitution(@PathVariable("instcode") String instCode) {
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		if (institution.isPresent()) {
			logger.info("Valid Institution Returned Successfully");
			return institution.get();
		} else {
			logger.info("Empty Institution Returned");
			return new Institution();
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Institution> createInstitution(@Valid @RequestBody Institution vinstitution) {
		if (vinstitution != null) {
			if (vinstitution.getInstcode() != null) {
				Optional<Institution> institution = institutionRepository.findByInstcode(vinstitution.getInstcode());
				if (!institution.isPresent()) {
					if (vinstitution.getId() == null) {
						if (vinstitution.getInstcode().length() >= 6) {
							Institution newInst = institutionRepository.save(vinstitution);
							logger.info("Institution record was Created Successfully");
							return new ResponseEntity<Institution>(newInst, HttpStatus.OK);
						} else {
							logger.info("Request is Invalid");
							return new ResponseEntity<Institution>(new Institution(), HttpStatus.BAD_REQUEST);
						}
					} else {
						logger.info("Request is Invalid");
						return new ResponseEntity<Institution>(new Institution(), HttpStatus.BAD_REQUEST);
					}
				} else {
					logger.info("Record Duplication not Allowed");
					return new ResponseEntity<Institution>(new Institution(), HttpStatus.CONFLICT);
				}
			} else {
				logger.info("Request is Invalid");
				return new ResponseEntity<Institution>(new Institution(), HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Request is Invalid");
			return new ResponseEntity<Institution>(new Institution(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update/{instcode}", method = RequestMethod.POST)
	public ResponseEntity<Institution> updateInstitution(@PathVariable("instcode") String instCode,
			@Valid @RequestBody Institution vinstitution) {
		if (vinstitution != null && vinstitution.getId() != null) {
			Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
			if (institution.isPresent()) {
				Institution newInst = institution.get();
				//Institution code and name cannot be amended
				if ((vinstitution.getInstcode().equals(newInst.getInstcode())) && (vinstitution.getName().equals(newInst.getName()))
						&& (vinstitution.getId().equals(newInst.getId()))) {
					newInst = institutionRepository.save(vinstitution);
					logger.info("Institution record was Updated Successfully");
					return new ResponseEntity<Institution>(newInst, HttpStatus.OK);
				} else {
					logger.info("Request not acceptable");
					return new ResponseEntity<Institution>(new Institution(), HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				logger.info("Institution record Not Found");
				return new ResponseEntity<Institution>(new Institution(), HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info("Request is Invalid");
			return new ResponseEntity<Institution>(new Institution(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/delete/{instcode}", method = RequestMethod.GET)
	public ResponseEntity<Institution> deleteInstitution(@PathVariable("instcode") String instCode) {
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		if (institution.isPresent()) {
			institutionRepository.delete(institution.get());
			logger.info("Institution record was Deleted Successfully");
			return new ResponseEntity<Institution>(institution.get(), HttpStatus.OK);
		} else {
			logger.info("Institution record Not Found");
			return new ResponseEntity<Institution>(new Institution(), HttpStatus.NOT_FOUND);
		}
	}
}
