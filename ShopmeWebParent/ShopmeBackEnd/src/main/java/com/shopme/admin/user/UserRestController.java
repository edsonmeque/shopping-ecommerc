package com.shopme.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @Autowired
	private UserService service ;
	
	// go verify if duplicate email in databese
	@PostMapping("/user/check_email")
	public String checkDuplicateEmail(@Param("uuid")Integer uuid,@Param("email") String email) {
		
	return service.isEmailUnique(uuid,email) ? "OK":"Duplicated";
	}
}
