package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		
		Role roleAdmin = new Role("Admin","manager everything");
		Role saveRole = repo.save(roleAdmin);
		assertThat(saveRole.getUuid()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSalesperson = new Role("Salesperson","manager product price, coustumer,"
				+ "shipping, orders and sale Report");
		
		Role roleEditor = new Role("Editor","manager categories, brands,products, articles and "
				+ "menu");
		Role roleShipper = new Role("Shipper","view product, view orders, update status");
		
		Role roleAssistant = new Role("Assistant","manager question and reviews");
		
		repo.saveAll(List.of(roleSalesperson,roleEditor, roleShipper, roleAssistant));
		
		
		
	}
}
