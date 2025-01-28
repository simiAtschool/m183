package com.helvetia.m295.libraryserver.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.helvetia.m295.libraryserver.common.User;

/**
 * Interface für DB-Zugang der Entity User
 * @version 1.0.0
 * @author Simon Fäs
 * @see User
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Sucht User nach Name
	 * @param name Name des Users
	 * @return Optional mit oder ohne User
	 */
	Optional<User> findByName(String name);
}