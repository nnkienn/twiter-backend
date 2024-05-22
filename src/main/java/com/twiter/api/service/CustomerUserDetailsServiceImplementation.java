package com.twiter.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.twiter.api.model.User;
import com.twiter.api.repositories.UserRepositories;

@Service
public class CustomerUserDetailsServiceImplementation implements UserDetailsService{
	@Autowired
	private UserRepositories userRepositories;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepositories.findByEmail(username);
		
		if(user== null || user.isLogin_with_google()) {
			throw new UsernameNotFoundException("username not found with email :" + username);
		}
		// TODO Auto-generated method stub
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}

}
