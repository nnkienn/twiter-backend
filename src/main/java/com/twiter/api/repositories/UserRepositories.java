package com.twiter.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.twiter.api.model.User;

@Repository
public interface UserRepositories extends JpaRepository<User, Long> {
	public User findByEmail(String email);

	@Query("SELECT DISTINCT u FROM User u WHERE u.fullName LIKE%:query% OR u.email LIKE %:query% ")
	public List<User> searchUser(@Param("query") String query); 
}
