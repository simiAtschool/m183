package com.helvetia.m295.libraryserver.security;

import java.util.*; 
import java.util.stream.Collectors; 
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.authority.SimpleGrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails;
import com.helvetia.m295.libraryserver.common.User;

/** 
 * Implementierung von {@link UserDetails}
 *  
 * @author Simon Fäs 
 * @see UserDetails
 */ 
public class LibraryUserDetails implements UserDetails {
	 private static final long serialVersionUID = 5286693029808370019L; 
	 
	    private String name; 
	    private String password; 
	    private List<GrantedAuthority> authorities; 
	    private boolean active; 
	    
	    /**
	     * Default constructor
	     */
	    public LibraryUserDetails(User user) { 
	        this.name = user.getName(); 
	        this.password = user.getPassword(); 
	        this.authorities = Arrays.stream(user.getRoles().split(",")) 
	                .map(SimpleGrantedAuthority::new) 
	                .collect(Collectors.toList()); 
	        this.active = user.isActive(); 
	    } 
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override 
	    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; } 
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override 
	    public String getPassword() { return password; } 
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override 
	    public String getUsername() { return name; } 
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override 
	    public boolean isAccountNonExpired() { return true; } 
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override 
	    public boolean isAccountNonLocked() { return true; } 
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override 
	    public boolean isCredentialsNonExpired() { return true; } 
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override 
	    public boolean isEnabled() { return active; }
}
