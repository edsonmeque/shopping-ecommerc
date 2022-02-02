package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserService {

	public  static int SIZA_PAGE_SIZE= 6;
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncode;
	
//get all user in database send to form_user	
	public List<User> listAll(){
		
		return (List<User>) userRepo.findAll();
	}
	
	//get all user  from page by page  7 / 7 
	public Page<User> listByPag(int pageNum,String sortField,String sortDir,String keyword){	
		
		Sort sort = Sort.by(sortField);
		
		sort =sortDir.equals("asc")? sort.ascending():sort.descending();
	    
		Pageable pageable = PageRequest.of(pageNum-1, SIZA_PAGE_SIZE,sort);
		
		if(keyword!=null) {
			return userRepo.findAll(keyword, pageable);
		}

		return userRepo.findAll(pageable);
	}
	
	
// get all role in database send to form_user
	public List<Role> allAllRoles(){
		
		return (List<Role>) roleRepo.findAll();
	}
//resaved data of user of form  save in database
	public User saveUser(User user) {
	   boolean isUpdatingUser = (user.getUuid()!=null);
	   
	   if(isUpdatingUser) {
		   
		   User exitingUser = userRepo.findById(user.getUuid()).get();
		   
		   if(user.getPassword().isEmpty()) {
			   
			   user.setPassword(exitingUser.getPassword());
		   }else {
			   
			   passwordEncode(user);
		   }
	   
	   }else {
		   passwordEncode(user);
	   }
		
		return userRepo.save(user);		
	}
// transform password in word cripting 
	public void passwordEncode(User user) {
		String encodePassword = passwordEncode.encode(user.getPassword());
		user.setPassword(encodePassword);
	}
	
	// this method verify if unique email
	public boolean isEmailUnique(Integer uuid, String email) {
		User userByEmail = userRepo.getUserByEmailUser(email);
		
		if(userByEmail==null) return  true;
			
		boolean isCreatedNew = (uuid==null);
		
		if(isCreatedNew) {
			  
			 if(userByEmail!=null) return false;
		}else {
			
			if(userByEmail.getUuid()!=uuid) {
				
				return false;
			}
		}
		return true;
	}
	
	public User getUuid(Integer uuid) throws UserNotFoundExeception   {
		try {
			return userRepo.findById(uuid).get();
			
		} catch (NoSuchElementException e) {
			
			throw new UserNotFoundExeception("Could not find any user with uuid"+uuid);
		}		
	}
	
	public void delete(Integer uuid) throws UserNotFoundExeception {

		User countById = userRepo.findById(uuid).get();
		
		if(countById==null) {
			throw new UserNotFoundExeception("Count not find any user with ID:"+uuid);
		}
		
		userRepo.deleteById(uuid);
     }
	
	public void updateUserEnabledStatus(Integer uuid, boolean enabled) {
	
		userRepo.updateEnableStatus(uuid, enabled);
	}
}
