package com.mmv;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.mmv.model.Role;
import com.mmv.model.User;
import com.mmv.repository.RoleRepository;
import com.mmv.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	/* Executed this first */
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("ravikumar@gmail.com");
		user.setPassword("ravi2020");
		user.setFirstName("Ravi");
		user.setLastName("Kumar");

		User savedUser = userRepo.save(user);

		User existUser = entityManager.find(User.class, savedUser.getId());

		assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

	}

	@Test
	public void testFindUserByEmail() {
		String email = "jane@gmail.com";
		User user = userRepo.findByEmail(email);
		assertThat(user).isNotNull();
	}

	/* Executed this second */
	@Test
	public void testAddRoleToNewUser() {

		User user = new User();
		user.setEmail("mikes.gates@gmail.com");
		user.setPassword("mike2020");
		user.setFirstName("Mike");
		user.setLastName("Gates");
		Role roleUser = roleRepo.findByName("User");
		user.addRole(roleUser);

		User savedUser = userRepo.save(user);

		assertThat(savedUser.getRoles().size()).isEqualTo(1);
	}

	@Test
	public void testAddRoleToExistingUser() {
		User user = userRepo.findById(1L).get();

		Role roleUser = roleRepo.findByName("User");
		user.addRole(roleUser);

		Role roleAdmin = new Role(2);
		user.addRole(roleAdmin);

		User savedUser = userRepo.save(user);

		assertThat(savedUser.getRoles().size()).isEqualTo(3);
	}
}
