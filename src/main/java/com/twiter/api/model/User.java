package com.twiter.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;
	private String location;
	private String websize; 
	private String birthDate;
	private String email;
	private String password;
	private String mobile;
	private String image;
	private String backgroundImage;
	private String bio;
	private boolean req_user;
	private boolean login_with_google;
	@JsonIgnore
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
	private List<Twit> twit = new ArrayList<>(); 
	
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
	private List<Like> like = new ArrayList<>(); 
	
	@Embedded
	private Varification verification;
	
	
	@ManyToMany
	private List<User> flowers = new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany
	private List<User> followings = new ArrayList<>();
	
	
	
}
