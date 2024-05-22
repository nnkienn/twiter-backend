package com.twiter.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twiter.api.config.JwtProvider;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.User;
import com.twiter.api.model.Varification;
import com.twiter.api.repositories.UserRepositories;
import com.twiter.api.response.AuthResponse;
import com.twiter.api.service.CustomerUserDetailsServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepositories userRepositories;
	
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CustomerUserDetailsServiceImplementation customerUserDetails;
	

	@Autowired
	private PasswordEncoder passwordEncoder;
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		String birthDay = user.getBirthDate();
		User isEmailExist = userRepositories.findByEmail(email);
		if(isEmailExist!=null)
		{
			throw new UserException("Email is already used with another account");
		}
		User createUser = new User();
		createUser.setEmail(email);
		createUser.setPassword(passwordEncoder.encode(user.getPassword()));
		createUser.setFullName(fullName);
		createUser.setBirthDate(birthDay);
		createUser.setVerification(new Varification());
		User saveUser = userRepositories.save(createUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.genrateToken(authentication);
		AuthResponse res = new AuthResponse(token,true);
		return new ResponseEntity<AuthResponse>(res,HttpStatus.CREATED);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody User user){
		String username = user.getEmail();
		String password = user.getPassword();
		
		Authentication authentication = autheticate(username, password);
		String token = jwtProvider.genrateToken(authentication);
		AuthResponse res = new AuthResponse(token,true);
		
		return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
		
	}
	private Authentication autheticate(String username, String password) {
		UserDetails userDetails = customerUserDetails.loadUserByUsername(username);
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid username .....");

		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password....");
			
			
			
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	}
	
}
