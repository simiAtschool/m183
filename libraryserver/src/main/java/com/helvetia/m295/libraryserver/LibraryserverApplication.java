package com.helvetia.m295.libraryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Klasse mit Einstiegsmethode main
 * 
 * @version 1.0.0
 * @author Simon Fäs
 */
@SpringBootApplication
public class LibraryserverApplication {

	/**
	 * Einstiegsmethode
	 * Startet Applikation
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(LibraryserverApplication.class, args);
	}

}
