package com.doc.manage.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.doc.manage.DTO.RoleDto;
import com.doc.manage.DTO.UserDto;
import com.doc.manage.entity.Role;
import com.doc.manage.entity.User;
import com.doc.manage.repository.RoleRepository;
import com.doc.manage.repository.UserDao;
import com.doc.manage.service.UserService;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private Set getAuthority(User user) {
        Set authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		userDao.deleteById(id);
	}

	@Override
	public User findOne(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findById(int id) {
		Optional<User> optionalUser = userDao.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

    @Override
    public UserDto update(UserDto userDto) {
        User user = findById(userDto.getId());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "roles","password");
            userDao.save(user);
        }
        return userDto;
    }
    
    @Override
    public UserDto updatePassword(UserDto userDto) {
        User user = findById(userDto.getId());
        if(user != null) {
        	user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            userDao.save(user);
        }
        return userDto;
    }
    

    @Override
    public User save(UserDto user) {
	    User newUser = new User();
	    newUser.setUsername(user.getUsername());
	    newUser.setFirstName(user.getFirstName());
	    newUser.setLastName(user.getLastName());
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setPermission(user.getPermission());
		newUser.setDepartment(user.getDepartment());
		newUser.setEmailId(user.getEmailId());
		if(user.getRoles() != null)
		{
			Collection<Role> extractedRole = new ArrayList<>();
			user.getRoles().forEach(role -> {
				Role exRole = roleRepo.findById(role.getId()).get();
				extractedRole.add(exRole);
			});
			newUser.setRoles(extractedRole);
		}
		
        return userDao.save(newUser);
    }
    
    @Override
    public UserDto findByUserName(String userName) {
    	
    	User user = userDao.findByUsername(userName);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
    	 UserDto newUser = new UserDto();
    	 
    	 
 	   
    	 BeanUtils.copyProperties(user, newUser, "password");
    	 newUser.setRoles(new ArrayList<RoleDto>());
    	 
    	 user.getRoles().forEach(roleIn -> {
    		 RoleDto role = new RoleDto();
        	 BeanUtils.copyProperties(roleIn, role);
        	 newUser.getRoles().add(role);
    	 });
 		
		return newUser;
    	
    }

	@Override
	public List<RoleDto> getAllRoles() {
		List<Role> allRoles = roleRepo.findAll();
		List<RoleDto> roleDtos = new ArrayList<>();
		allRoles.forEach(role -> {
			
			RoleDto roleDto = new RoleDto();
			//BeanUtils.copyProperties(role, roleDto);
			roleDto.setId(role.getId());
			roleDto.setName(role.getName());
			roleDto.setDescription(role.getDescription());
			roleDtos.add(roleDto);
		});
		
		
		return roleDtos;
	}
    
    
}
