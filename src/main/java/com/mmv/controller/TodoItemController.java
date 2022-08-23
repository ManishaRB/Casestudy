package com.mmv.controller;

import java.time.Instant;
import java.time.ZoneId;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mmv.model.TodoItem;
import com.mmv.repository.TodoItemRepository;
/*This controller shows list of tasks, renders to add new task page and update existing task */

@Controller
public class TodoItemController {
	private final Logger logger = LoggerFactory.getLogger(TodoItemController.class);

	@Autowired
	private TodoItemRepository todoItemRepository;

	@GetMapping("/todo_list")
	public ModelAndView index() {
		logger.info("request to GET index");
		ModelAndView modelAndView = new ModelAndView("todo_list");
		modelAndView.addObject("todoItems", todoItemRepository.findAll());
		modelAndView.addObject("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
		return modelAndView;
	}

	@PostMapping("/todo")
	public String createTodoItem(@Valid TodoItem todoItem, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-todo-item";
		}

		todoItem.setCreatedDate(Instant.now());
		todoItem.setModifiedDate(Instant.now());
		todoItemRepository.save(todoItem);
		return "redirect:/todo_list";
	}

	@PostMapping("/todo/{id}")
	public String updateTodoItem(@PathVariable("id") long id, @Valid TodoItem todoItem, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			todoItem.setId(id);
			return "update-todo-item";
		}

		todoItem.setModifiedDate(Instant.now());
		todoItemRepository.save(todoItem);
		return "redirect:/todo_list";
	}

}
