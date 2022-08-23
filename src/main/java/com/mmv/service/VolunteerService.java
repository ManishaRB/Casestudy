package com.mmv.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mmv.model.Volunteer;

public interface VolunteerService {
	List<Volunteer> getAllVolunteers();
	void saveVolunteer(Volunteer volunteer);
	Volunteer getVolunteerById(long id);
	void deleteVolunteerById(long id);
	Page<Volunteer> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
