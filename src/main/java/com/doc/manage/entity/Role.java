package com.doc.manage.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Role {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String name;
    
    private String description;
    
    
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users;
 
//    @ManyToMany
//    @JoinTable(
//        name = "roles_privileges", 
//        joinColumns = @JoinColumn(
//          name = "role_id", referencedColumnName = "id"), 
//        inverseJoinColumns = @JoinColumn(
//          name = "privilege_id", referencedColumnName = "id"))
//    private Collection<Privilege> privileges;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

//	public Collection<Privilege> getPrivileges() {
//		return privileges;
//	}
//
//	public void setPrivileges(Collection<Privilege> privileges) {
//		this.privileges = privileges;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   
    
}