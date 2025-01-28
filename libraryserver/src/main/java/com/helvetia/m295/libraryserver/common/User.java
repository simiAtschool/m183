package com.helvetia.m295.libraryserver.common;

import com.helvetia.m295.libraryserver.model.AusleiheRepository;
import com.helvetia.m295.libraryserver.service.AusleiheController;

import jakarta.persistence.*;

/**
 * Klasse für die DB-Entity User
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #id}: Eindeutiges Attribut des Users</li>
 * <li>{@link #name}: Einzigartiger Name des Users</li>
 * <li>{@link #password}: Passwort des Users</li>
 * <li>{@link #roles}: Rolle des Benutzers</li>
 * <li>{@link #active}: Attribut, um User zu deaktivieren</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author Simon Fäs
 * @see AusleiheRepository
 * @see AusleiheController
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String password;
	private String roles;
	private boolean active;

	/**
	 * Standard constructor
	 */
	public User() {}
	
	/**
	 * Constructor mit Name, Passwort und Rollen als Parameter
	 * @param name
	 * @param password
	 * @param roles
	 */
	public User(String name, String password, String roles) {
		this.name = name;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
