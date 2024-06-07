package com.twiter.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twiter.api.dto.UserDto;
import com.twiter.api.dto.mapper.UserDtoMapper;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.User;
import com.twiter.api.service.UserService;
import com.twiter.api.until.UserUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<UserDto> geUserProfile(@RequestHeader("Authorization") String jwt ) throws UserException{
		User user = userService.findUserProfileByTwit(jwt);
		UserDto userDto = UserDtoMapper.toUserDto(user);
		userDto.setReq_user(true);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
		
	}
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long userId,@RequestHeader("Authorization") String jwt ) throws UserException{
		User reqUser = userService.findUserProfileByTwit(jwt);
		User user = userService.findUserById(userId);
		UserDto userDto = UserDtoMapper.toUserDto(user);
		userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
		userDto.setFollowed(UserUtil.isFollowByReqUser(reqUser, user));
		return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<UserDto>> getUserById(@RequestParam String query,@RequestHeader("Authorization") String jwt ) throws UserException{
		User reqUser = userService.findUserProfileByTwit(jwt);
		
		List<User> users= userService.searchUser(query);
		List<UserDto> userDto = UserDtoMapper.toUserDtos(users);
		
		return new ResponseEntity<List<UserDto>>(userDto,HttpStatus.ACCEPTED);
		
	}

	@GetMapping("/update")
	public ResponseEntity<UserDto> getUserById(@RequestBody User req,@RequestHeader("Authorization") String jwt ) throws UserException{
		User reqUser = userService.findUserProfileByTwit(jwt);
		
		User user= userService.updateUser(reqUser.getId(), req);
		UserDto userDto = UserDtoMapper.toUserDto(user);
		
		return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
		
	}
	


	@PutMapping("{userId}/follow")
	public ResponseEntity<UserDto> searchUser(@PathVariable Long userId,@RequestHeader("Authorization") String jwt ) throws UserException{
		User reqUser = userService.findUserProfileByTwit(jwt);
		
		User user= userService.followUser(userId, reqUser);
		UserDto userDto = UserDtoMapper.toUserDto(user);
		userDto.setFollowed(UserUtil.isFollowByReqUser(reqUser, user));
		return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
		
	}
}
