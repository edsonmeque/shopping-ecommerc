package com.shopme.admin.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User,Integer> {
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmailUser(@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.email LIKE %?1%") 
	public Page<User> findAll(String keyword,Pageable pageable);
	
	//public Integer countById(@Param("uuid") Integer uuid);
	@Query("UPDATE User u SET u.enable= ?2 WHERE u.uuid = ?1")
	@Modifying
	public void updateEnableStatus(Integer uuid, boolean enable);
	
}
