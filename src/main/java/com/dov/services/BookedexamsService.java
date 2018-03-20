package com.dov.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dov.model.Answers;
import com.dov.model.Bookedexams;
import com.dov.model.Examinations;
import com.dov.model.Institution;
import com.dov.model.Questions;
import com.dov.model.Questionsbank;
import com.dov.model.User;
import com.dov.repository.InstitutionRepository;

@Service
public class BookedexamsService {

	@Autowired
	private InstitutionRepository institutionRepository;

	public Map<String, Object> validateBookingParameters(String instCode, String userName, String examCode) {
		Map<String, Object> model = new HashMap<>();
			Optional<Institution> institution = institutionRepository.findByInstcode(instCode);
			if (institution.isPresent()) {
				List<User> userList = institution.get().getUserList();
				Optional<User> user = userList.stream().filter(s -> s.getUsername().equals(userName)).findFirst();
				if (user.isPresent()) {
					List<Examinations> examList = institution.get().getExaminationsList();
					Optional<Examinations> examinations = examList.stream().filter(s -> s.getExamcode().equals(examCode)).findFirst();
					if (examinations.isPresent()) {
						model.put("RespMsg", "Valid Request");
						model.put("RespInfo", "Request is Valid");
						model.put("RespHttp", HttpStatus.OK);
						model.put("UserInfo", user.get());
						model.put("ExamInfo", examinations.get());
					} else {
						model.put("RespMsg", "Examination record Not Found");
						model.put("RespInfo", "Examination record Not Found");
						model.put("RespHttp", HttpStatus.NOT_FOUND);
					}
				} else {
					model.put("RespMsg", "User record Not Found");
					model.put("RespInfo", "User record Not Found");
					model.put("RespHttp", HttpStatus.NOT_FOUND);
				}
			} else {
				model.put("RespMsg", "Institution record Not Found");
				model.put("RespInfo", "Institution record Not Found");
				model.put("RespHttp", HttpStatus.NOT_FOUND);
			}
			return model;
	}

	public List<Questions> convertQuestBankToQuestions(List<Questionsbank> questionbankList){
		List<Questions> questList = new LinkedList<>();
		questionbankList.forEach(v -> {
			List<Answers> ansList = new LinkedList<>();
			Questions questions = new Questions(v.getMultipleanswer(), v.getQueorder(), v.getQuestion());
			v.getAnswersbankList().forEach(mval -> {
				Answers ans = new Answers(mval.getAnswers(), mval.getChoices(), mval.getCorrect(), mval.getAnsorder());
				ansList.add(ans);
			});
			questions.setAnswersList(ansList);
			questList.add(questions);
		});
		return questList;
	}	
	
	public Bookedexams updateBookedExam(Bookedexams oldBookedexams, Bookedexams newBookedexams){
		oldBookedexams.setEnddatetime(newBookedexams.getEnddatetime());
		oldBookedexams.setStartdatetime(newBookedexams.getStartdatetime());
		oldBookedexams.setStatus(newBookedexams.getStatus());
		oldBookedexams.setTotalcorrectanswers(newBookedexams.getTotalcorrectanswers());
		oldBookedexams.setTotalscore(newBookedexams.getTotalscore());
		return oldBookedexams;
	}
}
