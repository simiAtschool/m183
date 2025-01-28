package com.helvetia.m295.libraryserver.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helvetia.m295.libraryserver.common.Adresse;
import com.helvetia.m295.libraryserver.common.Kunde;
import com.helvetia.m295.libraryserver.common.User;
import com.helvetia.m295.libraryserver.model.AdresseRepository;
import com.helvetia.m295.libraryserver.model.KundeRepository;
import com.helvetia.m295.libraryserver.model.UserRepository;
import com.helvetia.m295.libraryserver.service.AusleiheController;
import com.helvetia.m295.libraryserver.service.KundeController;

/**
 * Klasse für Testfälle der Klasse {@link KundeController}
 * 
 * @version 1.0.0
 * @author Simon Fäs
 * @see KundeController
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username="admin", roles={"ADMIN"})
public class KundeControllerTests {

	private static final String END_POINT_PATH = "/kunde";
	private static final Long id = 1L;

	private Kunde testKunde;
	private Adresse testAdresse;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private KundeRepository kundeRepository;

	@MockBean
	private AdresseRepository adresseRepository;


	/**
	 * Constructor, um Test-Objekte zu initialisieren
	 */
	public KundeControllerTests() {
		this.testAdresse = new Adresse(id, "Zürcherstrasse 1", "Zürich", "8008");
		this.testKunde = new Kunde(id, "Hans", "Meier", new Date(), testAdresse, "hans.meier@gmail.com");
	}

	/**
	 * Test für {@link KundeController#getKundeById(Long)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Abrufen eines Kunden anhand seiner ID <br>
	 * Erwartet: Statuscode 200 und ein Kunde <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetIdShouldReturnOK() throws Exception {

		Mockito.when(kundeRepository.findById(id)).thenReturn(Optional.of(testKunde));

		mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/" + id).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testKunde)))
				.andDo(MockMvcResultHandlers.print());
	}
	
	/**
	 * Test für {@link AusleiheController#getAusleiheById(Long)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Abrufen eines nicht existenten Kunden anhand seiner ID. <br>
	 * Erwartet: Statuscode 404 <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetIdShouldReturnNotFound() throws Exception {

		Mockito.when(adresseRepository.findById(id)).thenReturn(Optional.empty());

		mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/" + id).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(404))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link KundeController#getKundeByNachname(String)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Abrufen von Kunden anhand ihres Nachnamens <br>
	 * Erwartet: Statuscode 200 und eine Liste an Kunden <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetNachnameShouldReturnOK() throws Exception {

		var listKunde = new ArrayList<Kunde>();
		listKunde.add(testKunde);
		Mockito.when(kundeRepository.findByNachname(testKunde.getNachname())).thenReturn(listKunde);

		mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/nachname/" + testKunde.getNachname())
				.contentType("application/json")).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listKunde)))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link KundeController#getKundeByAdresse(String)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Abrufen von Kunden anhand ihrer Adresse <br>
	 * Erwartet Statuscode 200 und eine Liste an Kunden <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAdresseShouldReturnOK() throws Exception {

		var listKunde = new ArrayList<Kunde>();
		listKunde.add(testKunde);
		Mockito.when(kundeRepository.findByAdresseAdresse(testKunde.getAdresse().getAdresse())).thenReturn(listKunde);

		mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/adresse/" + testKunde.getAdresse().getAdresse())
				.contentType("application/json")).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listKunde)))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link KundeController#addKunde(Kunde)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Hinzufügen eines neuen Kunden <br>
	 * Erwartet: Statuscode 200 <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPostShouldReturnOK() throws Exception {

		var listAdresse = new ArrayList<Adresse>();
		listAdresse.add(testAdresse);
		Mockito.when(adresseRepository.findByAdresseAndZip(testAdresse.getAdresse(), testAdresse.getZip()))
				.thenReturn(listAdresse);
		Mockito.when(adresseRepository.save(testAdresse)).thenReturn(testAdresse);
		Mockito.when(kundeRepository.save(testKunde)).thenReturn(testKunde);

		var json = objectMapper.writeValueAsString(testKunde);
		mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().is(200)).andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link KundeController#updateKunde(Kunde, Long)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Aktualisieren eines neuen Kunden <br>
	 * Erwartet: Statuscode 200 und aktualisierter Kunde <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPutShouldReturnOK() throws Exception {

		var listAdresse = new ArrayList<Adresse>();
		listAdresse.add(testAdresse);
		Mockito.when(adresseRepository.findByAdresseAndZip(testAdresse.getAdresse(), testAdresse.getZip()))
				.thenReturn(listAdresse);
		Mockito.when(adresseRepository.save(testAdresse)).thenReturn(testAdresse);
		Mockito.when(kundeRepository.findById(id)).thenReturn(Optional.of(testKunde));
		Mockito.when(kundeRepository.save(testKunde)).thenReturn(testKunde);

		var json = objectMapper.writeValueAsString(testKunde);
		mockMvc.perform(
				MockMvcRequestBuilders.put(END_POINT_PATH + "/" + id).contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.content().json(json))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link KundeController#deleteKunde(Long)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Löschen eines neuen Kunden <br>
	 * Erwartet: Statuscode 200 <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteShouldReturnOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" + id).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200)).andDo(MockMvcResultHandlers.print());
	}
	
	/**
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Berechtigung von Rolle "User" bei der Ressource "Kunde" bei GET Requests <br>
	 * Erwartet: Statuscode 403 <br>
	 * 
	 * @throws Exception	 
	 */
	@Test
	@WithMockUser(username="user", roles={"USER"})
	public void testGetWithRoleUser() throws Exception {
		Mockito.when(kundeRepository.findById(id)).thenReturn(Optional.of(testKunde));

		mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/" + id).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(403))
				.andDo(MockMvcResultHandlers.print());
	}
	
	
	/**
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Berechtigung von Rolle "User" bei der Ressource "Kunde" bei POST Requests <br>
	 * Erwartet: Statuscode 403 <br>
	 * 
	 * @throws Exception	 
	 */
	@Test
	@WithMockUser(username="user", roles={"USER"})
	public void testPostWithRoleUser() throws Exception {

		var listAdresse = new ArrayList<Adresse>();
		listAdresse.add(testAdresse);
		Mockito.when(adresseRepository.findByAdresseAndZip(testAdresse.getAdresse(), testAdresse.getZip()))
				.thenReturn(listAdresse);
		Mockito.when(adresseRepository.save(testAdresse)).thenReturn(testAdresse);
		Mockito.when(kundeRepository.save(testKunde)).thenReturn(testKunde);

		var json = objectMapper.writeValueAsString(testKunde);
		mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().is(403)).andDo(MockMvcResultHandlers.print());
	}
	
	
	/**
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Berechtigung von Rolle "User" bei der Ressource "Kunde" bei PUT Requests <br>
	 * Erwartet: Statuscode 403 <br>
	 * 
	 * @throws Exception	 
	 */
	@Test
	@WithMockUser(username="user", roles={"USER"})
	public void testPutWithRoleUser() throws Exception {

		var listAdresse = new ArrayList<Adresse>();
		listAdresse.add(testAdresse);
		Mockito.when(adresseRepository.findByAdresseAndZip(testAdresse.getAdresse(), testAdresse.getZip()))
				.thenReturn(listAdresse);
		Mockito.when(adresseRepository.save(testAdresse)).thenReturn(testAdresse);
		Mockito.when(kundeRepository.findById(id)).thenReturn(Optional.of(testKunde));
		Mockito.when(kundeRepository.save(testKunde)).thenReturn(testKunde);

		var json = objectMapper.writeValueAsString(testKunde);
		mockMvc.perform(
				MockMvcRequestBuilders.put(END_POINT_PATH + "/" + id).contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().is(403))
				.andDo(MockMvcResultHandlers.print());
	}
	
	
	/**
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Berechtigung von Rolle "User" bei der Ressource "Kunde" bei DELETE Requests <br>
	 * Erwartet: Statuscode 403 <br>
	 * 
	 * @throws Exception	 
	 */
	@Test
	@WithMockUser(username="user", roles={"USER"})
	public void testDeleteWithRoleUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" + id).contentType("application/json"))
		.andExpect(MockMvcResultMatchers.status().is(403)).andDo(MockMvcResultHandlers.print());
	}
}
