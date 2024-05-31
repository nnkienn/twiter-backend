package com.twiter.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twiter.api.exception.TwitException;
import com.twiter.api.exception.UserException;
import com.twiter.api.model.Twit;
import com.twiter.api.model.User;
import com.twiter.api.repositories.LikeRepository;
import com.twiter.api.repositories.TwitRepository;
import com.twiter.api.response.TwitReplyRequest;

@Service
public class TwitServicesImplement implements TwitService {
	@Autowired
	private TwitRepository twitRepositories;
	
	

	@Override
	public Twit createTwit(Twit req, User user) throws UserException {
		Twit twit = new Twit();
		twit.setContent(req.getContent());
		twit.setCreatedAt(LocalDateTime.now());
		twit.setImage(req.getImage());
		twit.setUser(user);
		twit.setReply(false);
		twit.setTwit(true);
		twit.setVideo(req.getVideo());
		return this.twitRepositories.save(twit);
	}

	@Override
	public List<Twit> findAllTwit() {
		// TODO Auto-generated method stub
		return twitRepositories.findAllByIsTwitTrueOrderByCreatedAtDesc();
	}

	@Override
	public Twit retwit(Long twitId, User user) throws UserException, TwitException {
		Twit twit = findById(twitId);
		if(twit.getRetwitUser().contains(user)) {
			twit.getRetwitUser().remove(user);
		}
		else {
			twit.getRetwitUser().add(user);
		}
		return twitRepositories.save(twit);
	}

	@Override
	public Twit findById(Long twitId) throws TwitException {
		Twit twit  = twitRepositories.findById(twitId).orElseThrow(()-> new TwitException("Twit not found with id" + twitId));
		
		// TODO Auto-generated method stub
		return twit;
	}

	@Override
	public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {

		Twit twit = findById(twitId);
		if(!userId.equals(twit.getUser().getId())) {
			throw new UserException("you can't delete anothor user's twit");
		}
		twitRepositories.delete(twit);
	}

	@Override
	public Twit removeFromRewit(Long twitId, User user) throws TwitException, UserException {
		
		return null;
	}

	@Override
	public Twit createdReply(TwitReplyRequest req, User user) throws TwitException, UserException {
		Twit replyFor =  findById(req.getTwitId());
		
		Twit twit = new Twit();
		twit.setContent(req.getContent());
		twit.setImage(req.getImage());
		twit.setUser(user);
		twit.setReply(false);
		twit.setTwit(true);
		twit.setReplyfor(replyFor);
		
		Twit savedReply = twitRepositories.save(twit);
		twit.getReplyTwit().add(savedReply);
		twitRepositories.save(replyFor);
		return replyFor;
	}

	@Override
	public List<Twit> getUserTwit(User user) {
		// TODO Auto-generated method stub
		return twitRepositories.findByRetwitUserContainsOrUser_idAndIsTwitTrueOrderByCreatedAtDesc(user, user.getId());
	}

	@Override
	public List<Twit> findByLikesContainsUser(User user) {
		return twitRepositories.findByLikesUser_Id(user.getId());
	}
}
