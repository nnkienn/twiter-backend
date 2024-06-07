package com.twiter.api.until;

import com.twiter.api.model.User;

public class UserUtil {
	public static final boolean isReqUser(User reqUser,User user2) {
		return reqUser.getId().equals(user2.getId());
	}
	public static final boolean isFollowByReqUser(User reqUser,User user2) {
		return reqUser.getFollowings().contains(user2);
	}}
