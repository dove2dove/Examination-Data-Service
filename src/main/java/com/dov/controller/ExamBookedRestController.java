package com.dov.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.dov.model.Bookedexams;
import com.dov.model.Examinations;
import com.dov.model.Institution;
import com.dov.model.Questions;
import com.dov.model.User;
import com.dov.repository.BookedexamsRepository;
import com.dov.repository.ExaminationsRepository;
import com.dov.repository.InstitutionRepository;
import com.dov.repository.UserRepository;
import com.dov.services.BookedexamsService;

@RestController
@RequestMapping("/Examination/Booking")
public class ExamBookedRestController {
	private static final Logger logger = Logger.getLogger(ExaminationRestController.class);

	@Autowired
	private BookedexamsRepository bookedexamsRepository;
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	@Autowired
	private ExaminationsRepository examinationsRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookedexamsService bookedexamsService;
	
	@RequestMapping(value = "/{instcode}/{username}", method = RequestMethod.GET)
	public List<Bookedexams> listUserBookedExamination(@PathVariable("instcode") String instCode, @PathVariable("username") String userName) {
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		if (institution.isPresent()) {
			return bookedexamsRepository.findByUser(userRepository.findByInstitutionAndUsername(institution.get(), userName).get());
		} else {
			return new ArrayList<>();
		}
	}

	@RequestMapping(value = "/{instcode}/{username}/{examcode}", method = RequestMethod.GET)
	public Bookedexams getBookedExamination(@PathVariable("instcode") String instCode, @PathVariable("username") String userName, @PathVariable("examcode") String examCode) {
		Bookedexams bookedexams = new Bookedexams();
		Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
		if (institution.isPresent()) {
		Optional<User> optuser = userRepository.findByInstitutionAndUsername(institution.get(), userName);	
		Optional<Examinations> optexaminations = examinationsRepository.findByInstitutionAndExamcode(institution.get(), examCode);
		if (optuser.isPresent() && optexaminations.isPresent()){
		Optional<Bookedexams> optbookedexam = bookedexamsRepository.findByUserAndExaminations(optuser.get(), optexaminations.get());
		if (optbookedexam.isPresent()) {
			bookedexams = optbookedexam.get();
			bookedexams.setQuestionsList(null);	
		}else{
			logger.info("Booked Exam record Not Found");
			bookedexams.setStatus("Booked Exam record Not Found");
		}
		}else{
			logger.info("Parameter Records Not Found");
			bookedexams.setStatus("Parameter Records Not Found");			
		}
		}else{
			logger.info("Wrong Institution Code!!!");
			bookedexams.setStatus("Wrong Institution Code!!!");
		}
		return bookedexams;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> UpdateBookedExamination(@Valid @RequestBody Bookedexams bookedexams, @RequestBody final Map<String, String> bookingParam) {
		if (bookedexams.getId() != null) {
			logger.info("Parameter Not Null");
			Optional<Institution> optinst = institutionRepository.findByInstcode((String) bookingParam.get("userName"));
			Optional<User> optuser = userRepository.findByInstitutionAndUsername(optinst.get(), (String) bookingParam.get("userName"));
			Optional<Examinations> optexaminations = examinationsRepository.findByInstitutionAndExamcode(optinst.get(), (String) bookingParam.get("examCode"));
			if (optuser.isPresent() && optexaminations.isPresent()){
				logger.info("User and Examination Not Null");
			Optional<Bookedexams> optbookedexam = bookedexamsRepository.findByUserAndExaminations(optuser.get(), optexaminations.get());
			if (optbookedexam.isPresent()) {
				logger.info("BookedExam found");
				if (optbookedexam.get().getStatus().equals("OPEN")) {
					logger.info("BookedExam found and its OPEN");
					Bookedexams newbookedexam = bookedexamsService.updateBookedExam(optbookedexam.get(), bookedexams);
					newbookedexam = bookedexamsRepository.save(newbookedexam);
					if ((newbookedexam != null) && (newbookedexam.getId() != null)){
						logger.info("Booked Examination was Successfully Updated");
						return new ResponseEntity<String>("Booked Examination was Successfully Updated", HttpStatus.OK);
					}else{
						logger.info("Booked Examination was Not Updated");
						return new ResponseEntity<String>("Booked Examination was Not Updated", HttpStatus.BAD_REQUEST);				
					}
				}else{
					logger.info("Cannot Update a Closed Booked Exam");
					return new ResponseEntity<String>("Cannot Update a Closed Booked Exam", HttpStatus.BAD_REQUEST);
				}
			}else{
				logger.info("Booked Exam record Not Found");
				return new ResponseEntity<String>("Booked Exam record Not Found", HttpStatus.NOT_FOUND);
			}
			}else{
				logger.info("Booked Exam record Not Found");
				return new ResponseEntity<String>("Booked Exam record Not Found", HttpStatus.NOT_FOUND);				
			}
		}else{
			logger.info("Invalid Request");
			return new ResponseEntity<String>("Invalid Request", HttpStatus.BAD_REQUEST);
		}
	}
	//
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> BookedExamination(@RequestBody final Map<String, String> bookingParam) {
		if (bookingParam.size() >= 3) {
			Map<String, Object> oldmodel = bookedexamsService.validateBookingParameters(bookingParam.get("instCode"), bookingParam.get("userName"), bookingParam.get("examCode"));
			if ((HttpStatus) oldmodel.get("RespHttp") == HttpStatus.OK) {
				Examinations examinations = (Examinations) oldmodel.get("ExamInfo");
				User user = (User) oldmodel.get("UserInfo");
				Bookedexams bookedexams = new Bookedexams();
				bookedexams.setExaminations(examinations);
				bookedexams.setUser(user);
				bookedexams.setStatus("OPEN");
				List<Questions> examquestions = bookedexamsService.convertQuestBankToQuestions(examinations.getQuestionsbankList());
				bookedexams.setQuestionsList(examquestions);	
				Bookedexams newbookedexam = bookedexamsRepository.save(bookedexams);
				if ((newbookedexam != null) && (newbookedexam.getId() != null)){
					logger.info("Examination was Successfully Booked for Candidate : " + user.getUsername());
					return new ResponseEntity<String>("Examination was Successfully Booked", HttpStatus.OK);
				}else{
					logger.info("Examination Not Booked for : " + user.getUsername());
					return new ResponseEntity<String>("Examination Not Booked", HttpStatus.BAD_REQUEST);				
				}
			} else {
				logger.info(oldmodel.get("RespInfo"));
				return new ResponseEntity<String>((String) oldmodel.get("RespMsg"), (HttpStatus) oldmodel.get("RespHttp"));
			}
		} else {
			logger.info("Invalid Request");
			return new ResponseEntity<String>("Invalid Request", HttpStatus.BAD_REQUEST);
		}
	}

	//
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<String> deleteBookedExamination(@RequestBody final Map<String, String> bookingParam) {
		if (bookingParam.size() >= 3) {
			Map<String, Object> oldmodel = bookedexamsService.validateBookingParameters(bookingParam.get("instCode"), bookingParam.get("userName"), bookingParam.get("examCode"));
			if ((HttpStatus) oldmodel.get("RespHttp") == HttpStatus.OK) {
				Optional<Bookedexams> bookedexam = bookedexamsRepository.findByUserAndExaminations((User) oldmodel.get("UserInfo"), (Examinations) oldmodel.get("ExamInfo"));
				if (bookedexam.isPresent()) {
						User user = (User) oldmodel.get("UserInfo");
						user.removeBookedexams(bookedexam.get());
						User vuser = userRepository.save(user);
						if ((vuser != null) && (vuser.getId() != null)){
							//bookedexamsRepository.delete(bookedexam.get());
							logger.info("Booked Exam Deleted Successfully");
							return new ResponseEntity<String>("Booked Exam Deleted Successfully", HttpStatus.OK);
						}else{
							logger.info("Booked Examination Not deleted");
							return new ResponseEntity<String>("Booked Examination Not deleted", HttpStatus.BAD_REQUEST);
						}
				} else {
					logger.info("Booked Exam record Not Found");
					return new ResponseEntity<String>("Booked Exam record Not Found", HttpStatus.NOT_FOUND);
				}
			} else {
				logger.info(oldmodel.get("RespInfo"));
				return new ResponseEntity<String>((String) oldmodel.get("RespMsg"), (HttpStatus) oldmodel.get("RespHttp"));
			}
		} else {
			logger.info("Invalid Request");
			return new ResponseEntity<String>("Invalid Request", HttpStatus.BAD_REQUEST);
		}
	}
}
