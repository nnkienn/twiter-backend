package com.twiter.api.service;

import java.util.List;

import com.twiter.api.exception.UserException;
import com.twiter.api.model.User;

public interface UserService {
	public User findUserById(Long userId) throws UserException;
	public User findUserProfileByTwit(String jwt) throws UserException;
	public User updateUser(Long userId,User user) throws UserException;
	public User followUser(Long userId,User user ) throws UserException;
	public List<User> searchUser(String query);
}
