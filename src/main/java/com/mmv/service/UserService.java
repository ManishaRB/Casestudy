package com.mmv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.mmv.model.Role;
import com.mmv.model.User;
import com.mmv.repository.RoleRepository;
import com.mmv.repository.UserRepository;

@Service
public class UserService {
	//@Autowired - indicates Bean needs to be automatically created by Spring Container
	@Autowired
	private UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	// @Autowired PasswordEncoder passwordEncoder;
	public void saveUserWithDefaultRole(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassword = encoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		Role roleUser = roleRepo.findByName("User");
		user.addRole(roleUser);
		userRepo.save(user);
	}

	public void save(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassword = encoder.encode(user.getPassword());
		user.setPassword(encodePassword);

		userRepo.save(user);
	}

	public List<User> listAll() {
		return userRepo.findAll();
	}

	public void registerDefaultUser(User user) {
		Role roleUser = roleRepo.findByName("User");
		user.addRole(roleUser);

		userRepo.save(user);
	}

	public void deleteUserById(long id) {
		this.userRepo.deleteById(id);
	}

	public User get(Long id) {
		return userRepo.findById(id).get();
	}

	public List<Role> listRoles() {
		return roleRepo.findAll();
	}

}