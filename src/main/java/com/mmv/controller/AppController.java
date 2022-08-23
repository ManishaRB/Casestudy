package com.mmv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mmv.model.Role;
import com.mmv.model.User;
import com.mmv.service.UserService;

/* This controller accepts get and post requests for home page,login, register,
 * Shows list of registered users with options of update, delete operations
 * */
@Controller
public class AppController {
	@Autowired
	private UserService service;

	@GetMapping("/")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegistration(User user) {
		service.saveUserWithDefaultRole(user);
		return "register_success";
	}

	@GetMapping("/list_users")
	public String viewUsersList(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
		User user = service.get(id);
		List<Role> listRoles = service.listRoles();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		return "user_form";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable(value = "id") Long id) {
		// call delete User method
		this.service.deleteUserById(id);
		return "redirect:/users";
	}

	@PostMapping("/users/save")
	public String saveUser(User user) {
		service.save(user);
		return "redirect:/list_users";
	}
}
