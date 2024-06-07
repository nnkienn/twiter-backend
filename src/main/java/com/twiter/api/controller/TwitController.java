package com.twiter.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.twiter.api.response.ApiResponse;
import com.twiter.api.response.TwitReplyRequest;
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
	public ResponseEntity<TwitDto> createTwit(@RequestBody Twit req, @RequestHeader("Authorization") String jwt)
			throws UserException, TwitException {
		User user = userServices.findUserProfileByTwit(jwt);
		Twit twit = twitServices.createTwit(req, user);
		TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);
		return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
	}

	@PutMapping("/{twitId}/retwet")
	public ResponseEntity<TwitDto> replyTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
			throws Exception, TwitException {
		User user = userServices.findUserProfileByTwit(jwt);
		Twit twit = twitServices.retwit(twitId, user);

		TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);
		return new ResponseEntity<>(twitDto, HttpStatus.OK);
	}

	@GetMapping("/{twitId}")
	public ResponseEntity<TwitDto> findTwitById(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
			throws Exception, TwitException {
		User user = userServices.findUserProfileByTwit(jwt);
		Twit twit = twitServices.findById(twitId);

		TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);
		return new ResponseEntity<>(twitDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{twitId}")
	public ResponseEntity<ApiResponse> deleteTwit(@PathVariable Long twitId,@RequestHeader("Authorization") String jwt) throws Exception,TwitException{
		User user = userServices.findUserProfileByTwit(jwt);
	twitServices.deleteTwitById(twitId, user.getId());
	ApiResponse apiRes = new ApiResponse();
	apiRes.setMessage("Delete sucess");
	apiRes.setStatus(true);
	return new ResponseEntity<>(apiRes,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TwitDto>> getAllTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
			throws Exception, TwitException {
		User user = userServices.findUserProfileByTwit(jwt);
		List<Twit> twit = twitServices.findAllTwit();
		

		List<TwitDto> twitDto = TwitDtoMapper.toTwitDtos(twit, user);
		return new ResponseEntity<>(twitDto, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TwitDto>> getUserAllTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
			throws Exception, TwitException {
		User user = userServices.findUserProfileByTwit(jwt);
		List<Twit> twit = twitServices.getUserTwit(user);
		

		List<TwitDto> twitDto = TwitDtoMapper.toTwitDtos(twit, user);
		return new ResponseEntity<>(twitDto, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<TwitDto>> findTwitByLikeContainsUser(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
			throws Exception, TwitException {
		User user = userServices.findUserProfileByTwit(jwt);
		List<Twit> twit = twitServices.findByLikesContainsUser(user);

		List<TwitDto> twitDto = TwitDtoMapper.toTwitDtos(twit, user);
		return new ResponseEntity<>(twitDto, HttpStatus.OK);
	}
	
	
	

}
