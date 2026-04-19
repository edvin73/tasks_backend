package com.example.task_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@EntityGraph(attributePaths = {"tasks"})
	List<User> findAll();
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	@EntityGraph(attributePaths = {"tasks"})
	List<User> findByUsernameStartsWithIgnoreCase(String username);

}
