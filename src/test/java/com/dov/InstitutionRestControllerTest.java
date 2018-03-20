package com.dov;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.dov.model.Institution;
import com.dov.repository.InstitutionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstitutionRestControllerTest {
	private static final Logger logger = Logger.getLogger(InstitutionRestControllerTest.class);
	@LocalServerPort
	private int port;

	@Autowired
	private InstitutionRepository institutionRepository;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void dcreateInstitutionPass() throws Exception {
//		String url = "http://localhost:" + port + "/institution/create";
//		Institution institution = new Institution();
//		institution.setInstcode("Abadina");
//		institution.setName("Abadina College");
//		ResponseEntity<Institution> response = restTemplate.postForEntity(url, institution, Institution.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		assertThat(response.getBody().getId()).isNotNull();
//		assertThat(response.getBody().getInstcode()).isEqualTo("Abadina");
	}
//	
//	@Test
//	@Transactional
//	public void jupdateInstitutionPass() throws Exception {
//		String url = "http://localhost:" + port + "/institution/update/{instcode}";
//		Optional<Institution> optinstitution = institutionRepository.findByInstcode("dovehigh");
//		Institution institution = optinstitution.get();
//		if (institution.getAddress().equals("5 Micheal Jackson Street, Ikoyi")) {
//			institution.setAddress("15, Kehinde Ogunnusi Street");
//		}else{
//			institution.setAddress("5 Micheal Jackson Street, Ikoyi");
//		}
//		String msg = institution.getAddress();
//		ResponseEntity<Institution> response = restTemplate.postForEntity(url, institution, Institution.class, "dovehigh");
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		assertThat(response.getBody().getId()).isNotNull();
//		assertThat(response.getBody().getAddress()).isEqualTo(msg);
//	}
//	
//	@Test
//	public void quserDeleteInstituitionPass() throws Exception {
//		String url = "http://localhost:" + port + "/institution/delete/{instcode}";
//		ResponseEntity<Institution> response = restTemplate.getForEntity(url, Institution.class, "Abadina");
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		Institution institution = response.getBody();
//		assertThat(institution.getInstcode()).isEqualTo("Abadina");
//		assertThat(institution.getName()).isEqualTo("Abadina College");
//	}
}
