package com.mmv.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.mmv.model.Volunteer;


public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
	Optional<Volunteer> findByEmail(String email);
}
