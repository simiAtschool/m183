package com.helvetia.m295.libraryserver.security;

import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.*; 
import org.springframework.stereotype.Service;

import com.helvetia.m295.libraryserver.common.User;
import com.helvetia.m295.libraryserver.model.UserRepository; 

/** 
 * Implementierung von {@link UserDetailsService}
 *  
 * @author Simon Fäs 
 * @see UserDetailsService
 */ 
@Service 
public class LibraryUserDetailsService implements UserDetailsService {
    @Autowired 
    UserRepository userRepository; 
 
    /**
     * {@inheritDoc}
     */
    @Override 
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException { 
        Optional<User> user = userRepository.findByName(username);
        System.out.println(user.isPresent());
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username)); 
        return user.map(LibraryUserDetails::new).get(); 
    } 
} 
