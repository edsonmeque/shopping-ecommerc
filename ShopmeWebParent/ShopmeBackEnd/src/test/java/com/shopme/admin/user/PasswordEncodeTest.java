package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeTest {

	@Test
	public  void testEncodePassword() {
		
		BCryptPasswordEncoder passwordencode = new BCryptPasswordEncoder();
		String password ="Edson1234";
		String encodePassword = passwordencode.encode(password);
		System.out.print(encodePassword);
		
		boolean matches = passwordencode.matches(password, encodePassword);
		assertThat(matches).isTrue();
	}
}
