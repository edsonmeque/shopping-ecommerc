package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
@DataJpaTest 
@AutoConfigureTestDatabase(replace = Replace.NONE)

@Rollback(false)
public class UserRepositoryTest {
 
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		
		Role adminRole = testEntityManager.find(Role.class,1);
		User userNamHM = new User("Edson Meque","nam 2021","Nam","Ha mimh");
		userNamHM.addRoles(adminRole);
		
		User savedUser = repo.save(userNamHM);
		assertThat(savedUser.getUuid()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		
		User userRevi= new User("Nelson Mutane","nam 2027","Muta","Ha Agora");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userRevi.addRoles(roleEditor);
		userRevi.addRoles(roleAssistant);
		
		User saveUser =repo.save(userRevi);
		assertThat(saveUser.getUuid()).isGreaterThan(0);
	}
	@Test
	public void testListUser() {
		Iterable<User> listUser = repo.findAll();
		listUser.forEach(user->System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userNam = repo.findById(1).get();
		System.out.print(userNam);
		assertThat(userNam).isNotNull();
	}
	
	@Test
	public  void testUpdateUserById() {
		User userNem = repo.findById(2).get();
		userNem.setEnable(true);
		userNem.setEmail("nelson@gmail.com");
		repo.save(userNem);
		
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId =4;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email ="nelson@gmail.com";
		User user = repo.getUserByEmailUser(email);
		assertThat(user).isNotNull();
}
	@Test
	
	public void testDesibledUser() {
		Integer  uuid = 2;
		repo.updateEnableStatus(uuid, false);
	}
	
	@Test
	public void testListFirstPage() {
		Integer  numberPage =0;
		Integer pageSize=4;
		
		Pageable pageable =PageRequest.of(numberPage, pageSize);
		Page<User> page = repo.findAll(pageable);
		List<User> listUser = page.getContent();
		
		listUser.forEach(user->System.out.print(user));
		assertThat(listUser.size()).isEqualTo(pageSize);	
	}
/*	
	@Test
	public void testCountById() {
		Integer id=10;
		Integer countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}*/
}
