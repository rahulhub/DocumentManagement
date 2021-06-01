package com.doc.manage.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.doc.manage.DTO.RoleDto;
import com.doc.manage.DTO.UserDto;
import com.doc.manage.entity.User;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
	UserDetails loadUserByUsername(String owner);
	UserDto findByUserName(String userName);
	List<RoleDto> getAllRoles();
	UserDto updatePassword(UserDto userDto);
}
