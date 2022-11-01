package com.example.spring_practice_jwt_2.repository;

import com.example.spring_practice_jwt_2.model.User;
import java.lang.StackWalker.Option;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
