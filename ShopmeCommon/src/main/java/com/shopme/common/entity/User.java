package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="users")

public class User {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uuid;
	
	@Column(name="firstName",length = 45, nullable = false)
	private String firstName;
	
	@Column(name="lastName",length = 45, nullable = false)
	private String lastName;
	
	@Column(name="email",length = 64, nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", length = 128)
	private String password; 
	
	@Column(length = 64)
	private String photos;
	
	private boolean enable;
	
	@ManyToMany
	@JoinTable(
			name="users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
				)
	
	private Set<Role> roles = new HashSet<>();


	
	
	
	public User() {
		
	}

	public User(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}


	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	public void addRoles(Role role) {
	
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return "User [uuid=" + uuid + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", roles=" + roles + "]";
	}
	
	@Transient
	public String photosImagePath() {
		
		if(uuid == null || photos==null) return "/image/default.png";
	
		return "/user-photos/"+this.uuid+"/"+this.photos;
	}
}
