package com.twiter.api.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.twiter.api.dto.LikeDto;
import com.twiter.api.dto.TwitDto;
import com.twiter.api.dto.UserDto;
import com.twiter.api.model.Like;
import com.twiter.api.model.User;

public class LikeDtoMapper {
	public static LikeDto toLikeDto(Like like,User reqUser) {
		UserDto user = UserDtoMapper.toUserDto(like.getUser());
		UserDto reqUserDto = UserDtoMapper.toUserDto(reqUser);
		TwitDto twit = TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);
		
		LikeDto likeDto = new LikeDto();
		likeDto.setId(like.getId());
		likeDto.setTwit(twit);
		likeDto.setUser(user);
		return likeDto;
	}
	public static List<LikeDto> toLikeDtos(List<Like> likes,User reqUser){
		List<LikeDto> likeDtos = new ArrayList<>();
		for(Like like:likes) {
			UserDto user = UserDtoMapper.toUserDto(like.getUser());
			TwitDto twit = TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);
			
			LikeDto likeDto = new LikeDto();
			likeDto.setId(like.getId());
			likeDto.setTwit(twit);
			likeDto.setUser(user);
			likeDtos.add(likeDto);
			
		}
		return likeDtos;
	}
}
