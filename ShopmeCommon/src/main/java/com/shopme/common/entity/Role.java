package com.shopme.common.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uuid;
	
	@Column(length = 40, nullable = false,unique = true)
	private String name;
	
	@Column(length = 140, nullable = false)
	private String Description;
	
	
	
	
	
	public Role() {
	}
	
	
	public Role(Integer uuid) {
		this.uuid = uuid;
	}


	public Role(String name) {
		
		this.name = name;
	}

	public Role(String name, String description) {
		
		this.name = name;
		Description = description;
	}
	
	public Integer getUuid() {
		return uuid;
	}
	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}


	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(uuid, other.uuid);
	}
	
	@Override
	public String toString() {
		return this.name;
	}	
}
