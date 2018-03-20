package com.dov.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.dov.model.Examinations;
import com.dov.model.Institution;
import com.dov.repository.ExaminationsRepository;
import com.dov.repository.InstitutionRepository;
import com.dov.services.ExaminationsService;

@RestController
@RequestMapping("/{instcode}/examination")
public class ExaminationRestController {

	private static final Logger logger = Logger.getLogger(ExaminationRestController.class);

	@Autowired
	private ExaminationsRepository examinationsRepository;
	
	@Autowired
	private ExaminationsService examinationsService;

	@Autowired
	private InstitutionRepository institutionRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Examinations> getExaminationList(@PathVariable("instcode") String instCode) {
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		if (institution.isPresent()) {
			return examinationsRepository.findByInstitution(institution.get());
		} else {
			return new ArrayList<>();
		}
	}

	@RequestMapping(value = "/{examcode}", method = RequestMethod.GET)
	public Examinations getExamination(@PathVariable("instcode") String instCode, @PathVariable("examcode") String examCode) {
		Examinations examinations = new Examinations();
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		if (institution.isPresent()) {
			Optional<Examinations> optexaminations = examinationsRepository.findByInstitutionAndExamcode(institution.get(), examCode);
			if (optexaminations.isPresent()) {
				examinations = optexaminations.get();
				logger.info("Examination Record found and returned!!!");
			} else {
				logger.info("Examination record not found!!!");
				examinations.setFulldescription("Examination record not found!!!");
			}
		} else {
			logger.info("Wrong Institution Code!!!");
			examinations.setFulldescription("Wrong Institution Code!!!");
		}
		return examinations;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Examinations> updateExaminations(@PathVariable("instcode") String instCode, @Valid @RequestBody Examinations vexaminations) {
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		Examinations examinations = new Examinations();
		if (institution.isPresent()) {
			if (vexaminations != null && vexaminations.getId() != null) {
				Optional<Examinations> optexaminations = examinationsRepository.findByInstitutionAndExamcode(institution.get(), vexaminations.getExamcode());
				if (optexaminations.isPresent()) {
					if ((vexaminations.getInstitution().getInstcode().equals(optexaminations.get().getInstitution().getInstcode()))
							&& (vexaminations.getExamcode().equals(optexaminations.get().getExamcode()))
							&& (vexaminations.getId().equals(optexaminations.get().getId()))) {						
						examinations = examinationsRepository.save(examinationsService.updateExaminationChildren(vexaminations));
						if ((examinations != null) && (examinations.getId() != null)){
							logger.info("Examination record was Updated Successfully");
							return new ResponseEntity<Examinations>(examinations, HttpStatus.OK);	
						}else{
							logger.info("Examination record failed to Update");
							examinations.setFulldescription("Examination record failed to Update");
							return new ResponseEntity<Examinations>(examinations, HttpStatus.BAD_REQUEST);	
						}
					} else {
						logger.info("Request not acceptable");
						examinations.setFulldescription("Request not acceptable");
						return new ResponseEntity<Examinations>(examinations, HttpStatus.NOT_ACCEPTABLE);
					}
				} else {
					logger.info("Examination record Not Found");
					examinations.setFulldescription("Examination record Not Found");
					return new ResponseEntity<Examinations>(examinations, HttpStatus.NOT_FOUND);
				}
			} else {
				logger.info("Request is Invalid");
				examinations.setFulldescription("Request is Invalid");
				return new ResponseEntity<Examinations>(examinations, HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Institution record Not Found");
			examinations.setFulldescription("Institution record Not Found");
			return new ResponseEntity<Examinations>(examinations, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Examinations> createExamination(@PathVariable("instcode") String instCode, @Valid @RequestBody Examinations vexaminations) {
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		Examinations examinations = new Examinations();
		if (institution.isPresent()) {
			if ((vexaminations != null) && (vexaminations.getExamcode() != null) && (vexaminations.getId() == null)) {
				Optional<Examinations> optexaminations = examinationsRepository.findByInstitutionAndExamcode(institution.get(), vexaminations.getExamcode());
				if (!optexaminations.isPresent()) {
					if (vexaminations.getExamcode().length() >= 6) {
						examinations = examinationsRepository.save(vexaminations);
						if ((examinations != null) && (examinations.getId() != null)){
							logger.info("Examination record was Created Successfully");
							return new ResponseEntity<Examinations>(examinations, HttpStatus.OK);
						}else{
							logger.info("Examination record failed to Update");
							examinations.setFulldescription("Examination record failed to Update");
							return new ResponseEntity<Examinations>(examinations, HttpStatus.BAD_REQUEST);							
						}
					} else {
						logger.info("Request is Invalid - ExamCode < 6");
						examinations.setFulldescription("Request is Invalid - ExamCode < 6");
						return new ResponseEntity<Examinations>(examinations, HttpStatus.BAD_REQUEST);
					}
				} else {
					logger.info("Record Duplication not Allowed");
					examinations.setFulldescription("Record Duplication not Allowed");
					return new ResponseEntity<Examinations>(examinations, HttpStatus.CONFLICT);
				}
			} else {
				logger.info("Request is Invalid - Wrong Exam Object");
				examinations.setFulldescription("Request is Invalid - Wrong Exam Object");
				return new ResponseEntity<Examinations>(examinations, HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("Institution record Not Found");
			examinations.setFulldescription("Institution record Not Found");
			return new ResponseEntity<Examinations>(examinations, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete/{examcode}", method = RequestMethod.GET)
	public ResponseEntity<Examinations> deleteExamination(@PathVariable("instcode") String instCode, @PathVariable("examcode") String examCode) {
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		Examinations examination = new Examinations();
		if (institution.isPresent()) {
					Optional<Examinations> examinations = examinationsRepository.findByInstitutionAndExamcode(institution.get(),examCode);
			if (examinations.isPresent()) {	
					institution.get().removeExamination(examinations.get());
					Institution vinstitution = institutionRepository.save(institution.get());
					if ((vinstitution != null) && (vinstitution.getId() != null)){
						logger.info("Examination record was Deleted Successfully");
						examination.setId(examinations.get().getId());
						examination.setFulldescription("Examination record was Deleted Successfully");
						return new ResponseEntity<Examinations>(examination, HttpStatus.OK);
					}else{
						logger.info("Examination record Not Deleted");
						examination.setFulldescription("Examination record Not Deleted");
						return new ResponseEntity<Examinations>(examination, HttpStatus.BAD_REQUEST);	
					}

			} else {
					logger.info("Examination record Not Found");
					examination.setFulldescription("Examination record Not Found");
					return new ResponseEntity<Examinations>(examination, HttpStatus.NOT_FOUND);
			}
		} else {
					logger.info("Institution record Not Found");
					examination.setFulldescription("Institution record Not Found");
					return new ResponseEntity<Examinations>(examination, HttpStatus.NOT_FOUND);
		}
	}
}
