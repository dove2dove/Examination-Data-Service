package com.dov;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dov.model.Bookedexams;
import com.dov.model.Institution;
import com.dov.repository.BookedexamsRepository;
import com.dov.repository.ExaminationsRepository;
import com.dov.repository.InstitutionRepository;
import com.dov.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExamBookedRestControllerTests {
	private static final Logger logger = Logger.getLogger(InstitutionRestControllerTest.class);

	@LocalServerPort
	private int port;
	
	@Autowired
	private BookedexamsRepository bookedexamsRepository;
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	@Autowired
	private ExaminationsRepository examinationsRepository;
	
	@Autowired
	private UserRepository  userRepository;
	
	@Autowired
	private TestRestTemplate restTemplate;
	

	@Test
	@Transactional
	public void rupdateBookedExamPass() throws Exception {
		String url = "http://localhost:" + port + "/Examination/Booking/update";
		Optional<Institution> institution = institutionRepository.findByInstcode("dovehigh");
		Optional<Bookedexams> bookedexam = bookedexamsRepository.findByUserAndExaminations(userRepository.findByInstitutionAndUsername(institution.get(), "vdwoodie").get(), examinationsRepository.findByInstitutionAndExamcode(institution.get(), "C00001").get());
		bookedexam.get().setStatus("CLOSE");	
		ResponseEntity<String> response = restTemplate.postForEntity(url, bookedexam, String.class, bookedexam);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Booked Examination was Successfully Updated");
	}
}
