package com.twiter.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twiter.api.exception.TwitException;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.Like;
import com.twiter.api.model.Twit;
import com.twiter.api.model.User;
import com.twiter.api.repositories.LikeRepository;
import com.twiter.api.repositories.TwitRepository;

@Service
public class LikeServiceImplement implements LikeService{


	@Autowired 
	private LikeRepository likeRepositories;
	
	@Autowired
	private TwitRepository twitRepositories;
	
	@Autowired
	private TwitService twitService;
	

	@Override
	public Like likeTwit(Long twitId, User user) throws UserException, TwitException {
		Like isLikeExit = likeRepositories.isLikeExist(user.getId(), twitId);
		if(isLikeExit !=null) {
			likeRepositories.deleteById(twitId);
			return isLikeExit;
		}
		Twit twit = twitService.findById(twitId);
		Like like = new Like();
		
		like.setTwit(twit);
		like.setUser(user);
		
		Like saveLike = likeRepositories.save(like);
		// TODO Auto-generated method stub
		
		twit.getLikes().add(saveLike);
		twitRepositories.save(twit);
		return saveLike;
	}

	@Override
	public List<Like> getAllLikes(Long twitId) throws TwitException {
		Twit twit = twitService.findById(twitId);
		List<Like> likes = likeRepositories.findByTwitId(twitId);
		
		return likes;
	}

}
