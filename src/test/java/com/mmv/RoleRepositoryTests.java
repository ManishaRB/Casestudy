package com.mmv;

import static org.assertj.core.api.Assertions.assertThat;



import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mmv.model.Role;
import com.mmv.repository.RoleRepository;

@DataJpaTest
//To execute SQL statements against database
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
 
    @Autowired private RoleRepository repo;
     
    @Test
    public void testCreateRoles() {
        Role user = new Role("User");
        Role admin = new Role("Admin");
        Role teacher = new Role("Teacher");
         
        repo.saveAll(List.of(user, admin, teacher));
         
        List<Role> listRoles = repo.findAll();
         
        assertThat(listRoles.size()).isEqualTo(3);
    }
     
}
