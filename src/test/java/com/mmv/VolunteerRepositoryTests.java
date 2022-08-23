package com.mmv;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.mmv.model.Volunteer;
import com.mmv.repository.VolunteerRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VolunteerRepositoryTests {

	@Autowired
	private VolunteerRepository volunteerRepository;

	// JUnit test for saveVolunteer
	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveVolunteerTest() {

		Volunteer volunteer = Volunteer.builder().email("thomas@gmail.com").firstName("Thomas").lastName("Rudov")
				.totalHours(0L).build();

		volunteerRepository.save(volunteer);

		assertThat(volunteer.getId()).isGreaterThan(0);
	}

	@Test
	@Order(2)
	public void getVolunteerTest() {

		Volunteer volunteer = volunteerRepository.findById(9L).get();

		assertThat(volunteer.getId()).isEqualTo(9L);

	}

	@Test
	@Order(3)
	public void getListOfVolunteersTest() {

		List<Volunteer> volunteers = volunteerRepository.findAll();
		assertThat(volunteers.size()).isGreaterThan(0);

	}

	@Test
	@Order(4)
	@Rollback(value = false)
	public void updateVolunteerTest() {

		Volunteer volunteer = volunteerRepository.findById(9L).get();

		volunteer.setEmail("tom@gmail.com");

		Volunteer volunteerUpdated = volunteerRepository.save(volunteer);

		assertThat(volunteerUpdated.getEmail()).isEqualTo("tom@gmail.com");

	}

	@Test
	@Order(5)
	@Rollback(value = false)
	public void deleteVolunteerTest() {

		Volunteer volunteer = volunteerRepository.findById(9L).get();

		volunteerRepository.delete(volunteer);

		Volunteer volunteer1 = null;

		Optional<Volunteer> optionalVolunteer = volunteerRepository.findByEmail("tom@gmail.com");

		if (optionalVolunteer.isPresent()) {
			volunteer1 = optionalVolunteer.get();
		}

		assertThat(volunteer1).isNull();
	}

}
