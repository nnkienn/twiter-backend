package com.twiter.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.twiter.api.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query("SELECT I FROM Like I WHERE I.user.id=:userId AN I.twit.id=:twitId ")
	public Like isLikeExist(@Param("userId") Long userId , @Param("twitId") Long twited);
	
	@Query("SELECT I FROM Like I WHERE I.twit.id=:twitId ")
	public List<Like>findByTwitId(@Param("twitId") Long twitId);
}
