package com.helvetia.m295.libraryserver.model;

import com.helvetia.m295.libraryserver.common.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface für DB-Zugang der Entity User
 *
 * @author Simon
 * @version 1.0.0
 * @see User
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Sucht User nach Name
     *
     * @param name Name des Users
     * @return Optional mit oder ohne User
     */
    Optional<User> findByName(String name);
}