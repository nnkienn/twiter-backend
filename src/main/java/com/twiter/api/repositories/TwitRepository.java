package com.twiter.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twiter.api.model.Twit;
import com.twiter.api.model.User;

public interface TwitRepository extends JpaRepository<Twit, Long> {
	List<Twit> findAllByIsTwitTrueOrderByCreatedAtDesc();

	List<Twit> findByRetwitUserContainsOrUser_idAndIsTwitTrueOrderByCreatedAtDesc(User user, Long userId);

	List<Twit> findByLikesContainingOrderByCreatedAtDesc(User user);
}
