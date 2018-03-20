package com.dov;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.dov.model.Answersbank;
import com.dov.model.Examinations;
import com.dov.model.Institution;
import com.dov.model.Questionsbank;

public class ExaminationTestData {
	private static final Logger logger = Logger.getLogger(ExaminationTestData.class);
	
	Map<String, Questionsbank> questionsList = new LinkedHashMap<>();
	Map<String, List<Answersbank>> answersList = new LinkedHashMap<>();

	public ExaminationTestData() {
		questionsList = getQuestionsBank();
		answersList = getAnswersBank();
		mergeQuestionsAnswers();
	}

	public Examinations getExaminations(Institution institution, String examCode) {
		Examinations examinations = new Examinations();
		examinations.setExamcode(examCode);
		examinations.setShortdescription("Test Exam Maker");
		examinations.setFulldescription("This exam is a Crossover exam test for selction of candidiate to positions within crossover");
		examinations.setTotalquestions(25);
		examinations.setPassscore(new BigDecimal(75.00));
		examinations.setInstitution(institution);
		examinations.setExamtime("1:30");
		examinations.setDurationmillsec(4200000);
		if (questionsList.size() == examinations.getTotalquestions()) {
			examinations.setQuestionsbankList(questionsList.values().stream().collect(Collectors.toList()));
		} else {
			examinations = null;
		}
		return examinations;
	}

	private void mergeQuestionsAnswers() {
		questionsList.forEach((mkey, mvalue) -> {
			mvalue.setAnswersbankList(answersList.get(mkey));
			questionsList.put(mkey, mvalue);
		});
	}


	private Map<String, Questionsbank> getQuestionsBank() {
		Map<String, Questionsbank> testquestions = new LinkedHashMap<>();
		//BooleanParse(1), 1 (if multiple Answer, displayorder
		testquestions.put("Q1", new Questionsbank(1, "What is correct syntax for main method of a java class?",BooleanParse(1)));
		testquestions.put("Q2", new Questionsbank(2, "Which of the following is not a keyword in java?",BooleanParse(0)));
		testquestions.put("Q3", new Questionsbank(3, "What is a class in java?", BooleanParse(0)));
		testquestions.put("Q4", new Questionsbank(4, "Primitive variables are stored on Stack.", BooleanParse(0)));
		testquestions.put("Q5", new Questionsbank(5, "Objects are stored on Stack.", BooleanParse(0)));
		testquestions.put("Q6", new Questionsbank(6, "Static functions can be accessed using null reference.",BooleanParse(0)));
		testquestions.put("Q7", new Questionsbank(7, "Can we compare int variable with a boolean variable?",BooleanParse(0)));
		testquestions.put("Q8", new Questionsbank(8, "What of the following is the default value of a local variable?", BooleanParse(0)));
		testquestions.put("Q9", new Questionsbank(9, "What of the following is the default value of an instance variable?", BooleanParse(0)));
		testquestions.put("Q10", new Questionsbank(10, "What is the size of byte variable?", BooleanParse(1)));
		testquestions.put("Q11", new Questionsbank(11, "What is the size of short variable?", BooleanParse(0)));
		testquestions.put("Q12", new Questionsbank(12, "What is the size of int variable?", BooleanParse(0)));
		testquestions.put("Q13", new Questionsbank(13, "What is the size of long variable?", BooleanParse(0)));
		testquestions.put("Q14", new Questionsbank(14, "What is the size of float variable?", BooleanParse(0)));
		testquestions.put("Q15", new Questionsbank(15, "What is the size of double variable?", BooleanParse(0)));
		testquestions.put("Q16", new Questionsbank(16, "What is the size of char variable?", BooleanParse(0)));
		testquestions.put("Q17", new Questionsbank(17, "What is the size of boolean variable?", BooleanParse(0)));
		testquestions.put("Q18", new Questionsbank(18, "Is an empty .java file a valid source file?",BooleanParse(0)));
		testquestions.put("Q19", new Questionsbank(19, "Can we have multiple classes in same java file?",BooleanParse(0)));
		testquestions.put("Q20", new Questionsbank(20, "Can we have two public classes in one java file?",BooleanParse(0)));
		testquestions.put("Q21", new Questionsbank(21, "What is the default value of byte variable?",BooleanParse(1)));
		testquestions.put("Q22", new Questionsbank(22, "What is the default value of short variable?",BooleanParse(0)));
		testquestions.put("Q23", new Questionsbank(23, "What is the default value of byte variable?",BooleanParse(0)));
		testquestions.put("Q24", new Questionsbank(24, "What is the default value of short variable?",BooleanParse(0)));
		testquestions.put("Q25", new Questionsbank(25, "What is the default value of int variable?",BooleanParse(0)));
		return testquestions;
	}

	private Map<String, List<Answersbank>> getAnswersBank() {
		Map<String, List<Answersbank>> answerMap = new LinkedHashMap<>();
		List<Answersbank> testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("public static int mainString[]args", 'A', BooleanParse(1),BooleanParse(0)));
		testAnswer.add(new Answersbank("public int mainString[]args", 'B', BooleanParse(0),BooleanParse(0)));
		testAnswer.add(new Answersbank("public static void mainString[]args", 'C', BooleanParse(1),BooleanParse(0)));
		testAnswer.add(new Answersbank("None of the above.", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q1", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("static", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("Boolean", 'B', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("void", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("private", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q2", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();	
		testAnswer.add(new Answersbank("A class is a blue print from which individual objects are created. A class can contain fields and methods to describe the behavior of an object.",'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("class is a special data type.", 'B', BooleanParse(0),BooleanParse(0)));
		testAnswer.add(new Answersbank("class is used to allocate memory to a data type.", 'C',BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("none of the above.", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q3", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("True", 'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("False", 'B', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q4", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("True", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("False", 'B', BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q5", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("True", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("False", 'B', BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q6", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("True", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("False", 'B', BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q7", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("null", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("0", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("Depends upon the type of variable", 'C', BooleanParse(0),BooleanParse(0)));
		testAnswer.add(new Answersbank("Not assigned", 'D', BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q8", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("null", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("0", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("Depends upon the type of variable", 'C', BooleanParse(1),BooleanParse(0)));
		testAnswer.add(new Answersbank("Not assigned", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q9", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("8 bit", 'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("16 bit", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("32 bit", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("64 bit", 'D', BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q10", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("8 bit", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("16 bit", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("32 bit", 'C', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("64 bit", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q11", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("8 bit", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("16 bit", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("32 bit", 'C', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("64 bit", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q12", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("8 bit", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("16 bit", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("32 bit", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("64 bit", 'D', BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q13", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("8 bit", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("16 bit", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("32 bit", 'C', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("64 bit", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q14", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("8 bit", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("16 bit", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("32 bit", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("64 bit", 'D', BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q15", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("8 bit", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("16 bit", 'B', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("32 bit", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("64 bit", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q16", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("8 bit", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("16 bit", 'B', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("32 bit", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("not precisely defined", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q17", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("True", 'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("False", 'B', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q18", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("True", 'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("False", 'B', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q19", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("True", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("False", 'B', BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q20", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("0", 'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("0.0", 'B', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("null", 'C', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("undefined", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q21", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("0.0", 'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("0", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("null", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("undefined", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q22", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("0", 'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("0.0", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("null", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("not defined", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q23", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("0.0", 'A', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("0", 'B', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("null", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("not defined", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q24", deepCloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Answersbank("0", 'A', BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Answersbank("0.0", 'B', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("null", 'C', BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Answersbank("not defined", 'D', BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q25", deepCloneAnswerList(testAnswer));
		return answerMap;
	}

	private boolean BooleanParse(int flag) {
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	private List<Answersbank> deepCloneAnswerList(List<Answersbank> ansList) {
		List<Answersbank> tempList = new ArrayList<>();
		//HibernateBeanReplicator replicator = new Hibernate3BeanReplicator();
		for (Answersbank Answers : ansList) {
			//Answersbank AnswersCopy = replicator.deepCopy(Answers);
			tempList.add(Answers);
		}
		return tempList;
	}
}
