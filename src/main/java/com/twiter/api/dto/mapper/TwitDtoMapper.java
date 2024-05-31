package com.twiter.api.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.twiter.api.dto.TwitDto;
import com.twiter.api.dto.UserDto;
import com.twiter.api.model.Twit;
import com.twiter.api.model.User;
import com.twiter.api.until.TwitUnit;

public class TwitDtoMapper {
	public static TwitDto toTwitDto(Twit twit,User reqUser) {
		UserDto user = UserDtoMapper.toUserDto(twit.getUser());
		boolean isLiked = TwitUnit.isLikedByReqUser(reqUser,twit);
		boolean isRewited = TwitUnit.isLikedByReqUser(reqUser, twit);
		List<Long> rewitUserId = new ArrayList<>();
		for(User user1:twit.getRetwitUser()) {
			rewitUserId.add(user1.getId());
		}
		TwitDto twitDto = new TwitDto();
		twitDto.setId(twit.getId());
		twitDto.setContent(twit.getContent());
		twitDto.setCreatedAt(twit.getCreatedAt());
		twitDto.setImage(twit.getImage());
		twitDto.setTotalLikes(twit.getLikes().size());
		twitDto.setTotalReplies(twit.getReplyTwit().size());
		twitDto.setTotalRewit(twit.getRetwitUser().size());
		twitDto.setLiked(isLiked);
		twitDto.setUser(user);
		twitDto.setRewit(isRewited);
		twitDto.setReplyTwits(toTwitDtos(twit.getReplyTwit(), reqUser));
		twitDto.setVideo(twit.getVideo());
		return twitDto;
	}
	
	public static List<TwitDto> toTwitDtos(List<Twit> twits,User reqUser){
		List<TwitDto> twitDtos = new ArrayList<>();
		for(Twit twit:twits) {
			TwitDto twitDto = toReplyTwitDto(twit,reqUser);
			twitDtos.add(twitDto);
		}
		return twitDtos;
	}

	private static TwitDto toReplyTwitDto(Twit twit, User reqUser) {
		UserDto user = UserDtoMapper.toUserDto(twit.getUser());
		boolean isLiked = TwitUnit.isLikedByReqUser(reqUser,twit);
		boolean isRewited = TwitUnit.isLikedByReqUser(reqUser, twit);
		List<Long> rewitUserId = new ArrayList<>();
		for(User user1:twit.getRetwitUser()) {
			rewitUserId.add(user1.getId());
		}
		TwitDto twitDto = new TwitDto();
		twitDto.setId(twit.getId());
		twitDto.setContent(twit.getContent());
		twitDto.setCreatedAt(twit.getCreatedAt());
		twitDto.setImage(twit.getImage());
		twitDto.setTotalLikes(twit.getLikes().size());
		twitDto.setTotalReplies(twit.getReplyTwit().size());
		twitDto.setTotalRewit(twit.getRetwitUser().size());
		twitDto.setLiked(isLiked);
		twitDto.setUser(user);
		twitDto.setRewit(isRewited);
		twitDto.setVideo(twit.getVideo());
		return twitDto;
	}
}
