package com.twiter.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


@Data
public class TwitDto {
	private Long id;
	
	private String content;
	
	private String image;
	
	private String video;
	
	
	private UserDto user;
	
	private LocalDateTime createdAt;
	
	
	private int totalLikes;
	
	private int totalReplies;
	
	private int totalRewit;
	
	private boolean isLiked;
	
	private boolean isRewit;
	
	private List<Long> rewitUsersId;
	
	private List<TwitDto> replyTwits;
}
