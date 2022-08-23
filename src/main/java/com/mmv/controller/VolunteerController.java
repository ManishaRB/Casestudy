package com.mmv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmv.model.Volunteer;
import com.mmv.service.VolunteerService;
/* This VolunteerController shows new volunteer form to add new volunteer
 * Save the newly added volunteer
 * Update information of existing volunteer
 * Shows Pagination and sort columns ASC or DESC*/

@Controller
public class VolunteerController {
	@Autowired
	private VolunteerService volunteerService;
	
	// display list of volunteers
	@GetMapping("/vlist")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewVolunteerForm")
	public String showNewVolunteerForm(Model model) {
		// create model attribute to bind form data
		Volunteer volunteer = new Volunteer();
		model.addAttribute("volunteer", volunteer);
		return "new_volunteer";
	}
	
	@PostMapping("/saveVolunteer")
	public String saveVolunteer(@ModelAttribute("volunteer") Volunteer volunteer) {
		// save volunteer to database
		volunteerService.saveVolunteer(volunteer);
		return "redirect:/vlist";
		
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get volunteer from the service
		Volunteer volunteer = volunteerService.getVolunteerById(id);
		
		// set volunteer as a model attribute to pre-populate the form
		model.addAttribute("volunteer", volunteer);
		return "update_volunteer";
	}
	
	@GetMapping("/deleteVolunteer/{id}")
	public String deleteVolunteer(@PathVariable (value = "id") long id) {
		
		// call delete volunteer method 
		this.volunteerService.deleteVolunteerById(id);
		return "redirect:/vlist";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 8;
		
		Page<Volunteer> page = volunteerService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Volunteer> listVolunteers = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listVolunteers", listVolunteers);
		return "vlist";
	}

}
