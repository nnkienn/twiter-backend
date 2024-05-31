package com.twiter.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twiter.api.config.JwtProvider;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.User;
import com.twiter.api.repositories.UserRepositories;

@Service
public class UserServiceImplement implements UserService {

	@Autowired
	public UserRepositories userRepositories;

	@Autowired
	public JwtProvider jwtProvider;
	@Override
	public User findUserById(Long userId) throws UserException {
		User user = userRepositories.findById(userId)
				.orElseThrow(() -> new UserException("User not found with id" + userId));

		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public User findUserProfileByTwit(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		User user = userRepositories.findByEmail(email);
		return user;
	}

	@Override
	public User updateUser(Long userId, User req) throws UserException {
		User user = userRepositories.findById(userId)
				.orElseThrow(() -> new UserException("User not found with id" + userId));

		if (req.getFullName() != null) {
			user.setFullName(req.getFullName());

		}
		if (req.getImage() != null) {
			user.setImage(req.getImage());
		}
		if (req.getBirthDate() != null) {
			user.setBirthDate(req.getBirthDate());
		}
		if (req.getBackgroundImage() != null) {
			user.setBackgroundImage(req.getBackgroundImage());
		}
		if (req.getLocation() != null) {
			user.setLocation(req.getLocation());
		}
		if (req.getBio() != null) {
			user.setBio(req.getBio());
		}
		if (req.getWebsize() != null) {
			user.setWebsize(req.getWebsize());
		}
		return user;
	}

	@Override
	public User followUser(Long userId, User user) throws UserException {
		User followToUser = findUserById(userId);
		if (user.getFollowings().contains(followToUser) && followToUser.getFlowers().contains(user)) {
			user.getFollowings().remove(followToUser);
			followToUser.getFlowers().remove(user);
		} else {
			user.getFollowings().add(followToUser);
			followToUser.getFlowers().add(user);
		}
		userRepositories.save(followToUser);
		userRepositories.save(user);
		return followToUser;
	}

	@Override
	public List<User> searchUser(String query) {
		// TODO Auto-generated method stub
		return userRepositories.searchUser(query);
	}

}
