package com.twiter.api.service;

import java.util.List;

import com.twiter.api.exception.TwitException;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.Like;
import com.twiter.api.model.User;

public interface LikeService {
	public Like likeTwit(Long twitId,User user) throws UserException, TwitException;
	public List<Like> getAllLikes(Long twitId) throws TwitException;
}
