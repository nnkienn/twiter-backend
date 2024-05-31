package com.twiter.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twiter.api.dto.TwitDto;
import com.twiter.api.dto.mapper.TwitDtoMapper;
import com.twiter.api.exception.TwitException;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.Twit;
import com.twiter.api.model.User;
import com.twiter.api.service.TwitService;
import com.twiter.api.service.UserService;

@RestController
@RequestMapping("/api/twit")
public class TwitController {

		@Autowired
		private TwitService twitServices;
		
		@Autowired
		private UserService userServices;
		
		@PostMapping("/create")
		public ResponseEntity<TwitDto> createTwit(@RequestBody Twit req,@RequestHeader("Authorization") String jwt) throws UserException,TwitException{
			User user= userServices.findUserProfileByTwit(jwt);
			Twit twit = twitServices.createTwit(req, user);
			TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);
			return new ResponseEntity<>(twitDto,HttpStatus.CREATED);
		}
	
	
	
}
