package com.helvetia.m295.libraryserver.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Klasse für Konfigurationen
 * 
 * @version 1.0.0
 * @author Simon Fäs
 */
@Configuration
@Profile("development")
public class DevConfiguration implements WebMvcConfigurer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/bibliothek/**").addResourceLocations("classpath:/public/");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*");
	}

}
