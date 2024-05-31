package com.twiter.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.twiter.api.exception.TwitException;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.Twit;
import com.twiter.api.model.User;
import com.twiter.api.response.TwitReplyRequest;

public interface TwitService {
	public Twit createTwit(Twit req, User user) throws UserException;

	public List<Twit> findAllTwit();

	public Twit retwit(Long twitId, User user) throws UserException, TwitException;

	public Twit findById(Long twitId) throws TwitException;

	public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException;

	public Twit removeFromRewit(Long twitId, User user) throws TwitException, UserException;

	public Twit createdReply(TwitReplyRequest req, User userId) throws TwitException, UserException;

	public List<Twit> getUserTwit(User user);

	public List<Twit> findByLikesContainsUser(User userId);
}
