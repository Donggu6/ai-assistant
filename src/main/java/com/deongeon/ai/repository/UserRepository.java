package com.deongeon.ai.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deongeon.ai.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}
