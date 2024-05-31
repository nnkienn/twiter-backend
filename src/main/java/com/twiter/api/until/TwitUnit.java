package com.twiter.api.until;

import com.twiter.api.model.Like;
import com.twiter.api.model.Twit;
import com.twiter.api.model.User;

public class TwitUnit {
	public final static boolean isLikedByReqUser(User reqUser,Twit twit) {
		for(Like like : twit.getLikes()){
			if(like.getUser().getId().equals(reqUser.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public final static boolean isRewitedByReqUser(User reqUser,Twit twit) {
		for(User user : twit.getRetwitUser()){
			if(user.getId().equals(reqUser.getId())) {
				return true;
			}
		}
		return false;
	}

}
