package com.helvetia.m295.libraryserver.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PepperedBCryptPasswordEncoder extends BCryptPasswordEncoder {

    @Value("${security.pepper}")
    private String pepper;  // Pepper aus der Konfiguration (application.properties)

    public PepperedBCryptPasswordEncoder(int strength) {
        super(strength);  // Stärke für den BCrypt-Algorithmus festlegen
    }

    // Standard-Konstruktor, der den Pepper aus der Konfiguration lädt
    public PepperedBCryptPasswordEncoder() {
        super();  // Standardstärke von 10 verwenden
    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        // Füge den Pepper zum Passwort hinzu und hash es dann
        String passwordWithPepper = rawPassword.toString() + pepper;
        return super.encode(passwordWithPepper);  // Nutze die bestehende Hashing-Logik
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        // Füge den Pepper zum rawPassword hinzu und vergleiche es mit dem gespeicherten gehashten Passwort
        String passwordWithPepper = rawPassword.toString() + pepper;
        return super.matches(passwordWithPepper, encodedPassword);
    }
}