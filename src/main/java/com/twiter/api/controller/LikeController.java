package com.twiter.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twiter.api.dto.LikeDto;
import com.twiter.api.dto.mapper.LikeDtoMapper;
import com.twiter.api.exception.TwitException;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.Like;
import com.twiter.api.model.User;
import com.twiter.api.service.LikeService;
import com.twiter.api.service.UserService;

@RestController
@RequestMapping("/api")
public class LikeController {
	@Autowired
	private UserService userServices;
	
	@Autowired
	private LikeService likeServices;
	
	@PostMapping("/{twitId}/likes")
	public ResponseEntity<LikeDto> likeTwit(@PathVariable Long TwiId,@RequestHeader("Authorization") String jwt) throws UserException,TwitException{
		User user =userServices.findUserProfileByTwit(jwt);
		Like like = likeServices.likeTwit(TwiId, user);
		LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user);
		return new ResponseEntity<LikeDto>(likeDto,HttpStatus.CREATED);
		
	}
	@PostMapping("/twit/{twitId}")
	public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long TwiId,@RequestHeader("Authorization") String jwt) throws UserException,TwitException{
		User user =userServices.findUserProfileByTwit(jwt);
		List<Like> like = likeServices.getAllLikes(TwiId);
		List<LikeDto> likeDto = LikeDtoMapper.toLikeDtos(like, user);
		return new ResponseEntity<List<LikeDto>>(likeDto,HttpStatus.CREATED);
		
	}
	
	
}
