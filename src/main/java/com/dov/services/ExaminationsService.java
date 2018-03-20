package com.dov.services;

import org.springframework.stereotype.Service;

import com.dov.model.Examinations;

@Service
public class ExaminationsService {

	public Examinations updateExaminationChildren(Examinations examinations){
		for(int k=0; k < examinations.getQuestionsbankList().size(); k++){
			examinations.getQuestionsbankList().get(k).updateAnswersBank();
		}
		return examinations;
	}
}
