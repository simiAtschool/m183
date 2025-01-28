package com.helvetia.m295.libraryserver.security;

import org.springframework.security.crypto.password.PasswordEncoder; 

/** 
 * Da die Passwoerter in der Datenbank nicht verschluesselt abgelegt sind, wird 
 * diese Hilfsklasse zu Demonstrationszwecken verwendet. Diese Klasse unbedingt 
 * in SecurityConfig durch BCryptPasswordEncoder ersetzen! 
 *  
 * @author vonarma1 
 * @see org.springframework.security.crypto.password.PasswordEncoder 
 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder 
 */ 
@Deprecated 
public class PasswordEncoderDummy implements PasswordEncoder { 
 
    /** {@inheritDoc} */ 
    @Override 
    public String encode(CharSequence rawPassword) { 
        return rawPassword.toString(); 
    } 
 
    /** {@inheritDoc} */ 
    @Override 
    public boolean matches(CharSequence rawPassword, String encodedPassword) { 
        return rawPassword.toString().equals(encodedPassword); 
    } 
} 
