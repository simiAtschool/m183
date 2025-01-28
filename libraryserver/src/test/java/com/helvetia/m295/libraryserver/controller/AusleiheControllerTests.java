package com.helvetia.m295.libraryserver.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helvetia.m295.libraryserver.common.*;
import com.helvetia.m295.libraryserver.model.AusleiheRepository;
import com.helvetia.m295.libraryserver.model.KundeRepository;
import com.helvetia.m295.libraryserver.model.MediumRepository;
import com.helvetia.m295.libraryserver.service.AusleiheController;

/**
 * Klasse für Testfälle der Klasse {@link AusleiheController} <br>
 * Berechtigungen müssen nicht getestet werden, da standardmässig schon die Rolle "USER" verwendet wird und "USER" weniger Rechte hat, als "ADMIN".
 * 
 * @version 1.0.0
 * @author Simon Fäs
 * @see AusleiheController
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username="user", roles={"USER"})
public class AusleiheControllerTests {

	private static final String END_POINT_PATH = "/ausleihe";
	private static final Long id = 1L;

	private Ausleihe testAusleihe;
	private Medium testMedium;
	private Kunde testKunde;
	private Adresse testAdresse;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AusleiheRepository ausleiheRepository;

	@MockBean
	private KundeRepository kundeRepository;

	@MockBean
	private MediumRepository mediumRepository;

	/**
	 * Constructor, um Test-Objekte zu initialisieren
	 */
	public AusleiheControllerTests() {
		this.testAdresse = new Adresse(id, "Zürcherstrasse 1", "Zürich", "8008");
		this.testKunde = new Kunde(id, "Hans", "Meier", new Date(), testAdresse, "hans.meier@gmail.com");
		this.testMedium = new Medium(id, "Lord of the Rings", "J.R.R Tolkien", "Fantasy", (short) 13, 9803478347812L,
				"A1");
		this.testAusleihe = new Ausleihe(id, testKunde, testMedium);
	}

	/**
	 * Test für {@link AusleiheController#getAusleiheById(Long)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Abrufen von Ausleihen anhand der Medium-ID. <br>
	 * Erwartet: Statuscode 200 und eine Liste an Objekten. <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMediumIdShouldReturnOK() throws Exception {

		Mockito.when(ausleiheRepository.findByMediumId(id)).thenReturn(List.of(testAusleihe));

		mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/" + id).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(testAusleihe))))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link AusleiheController#addNewAusleihe(Ausleihe)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Erstellen einer Ausleihe. <br>
	 * Erwartet: Statuscode 200 <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPostShouldReturnCreated() throws Exception {
		Mockito.when(mediumRepository.findById(id)).thenReturn(Optional.of(testMedium));
		Mockito.when(kundeRepository.findById(id)).thenReturn(Optional.of(testKunde));
		Mockito.when(ausleiheRepository.findByMediumId(id)).thenReturn(new ArrayList<Ausleihe>());
		Mockito.when(ausleiheRepository.save(testAusleihe)).thenReturn(testAusleihe);

		var json = objectMapper.writeValueAsString(testAusleihe);
		mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link AusleiheController#addNewAusleihe(Ausleihe)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Handhabung, wenn nicht existierenden Objekten. <br>
	 * Erwartet: Statuscode 400 <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPostShouldReturnBadRequest() throws Exception {
		Mockito.when(mediumRepository.findById(id)).thenReturn(Optional.of(testMedium));
		Mockito.when(kundeRepository.findById(id)).thenReturn(Optional.of(testKunde));
		Mockito.when(ausleiheRepository.findByMediumId(id)).thenReturn(new ArrayList<Ausleihe>());
		Mockito.when(ausleiheRepository.save(testAusleihe)).thenReturn(testAusleihe);

		mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link AusleiheController#addNewAusleihe(Ausleihe)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Handhabung einer Referenz auf einen fehlenden Kunden oder auf ein
	 * fehlendes Medium <br>
	 * Erwartet: Statuscode 404
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPostShouldReturnNotFound() throws Exception {
		Mockito.when(mediumRepository.findById(id)).thenReturn(Optional.empty());
		Mockito.when(kundeRepository.findById(id)).thenReturn(Optional.of(testKunde));
		Mockito.when(ausleiheRepository.findByMediumId(id)).thenReturn(new ArrayList<Ausleihe>());
		Mockito.when(ausleiheRepository.save(testAusleihe)).thenReturn(testAusleihe);

		mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json")
				.content(objectMapper.writeValueAsString(testAusleihe)))
				.andExpect(MockMvcResultMatchers.status().is(404))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link AusleiheController#addNewAusleihe(Ausleihe)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Handhabung, wenn eine Ausleihe ein Medium bereits referenziert <br>
	 * Erwartet: Statuscode 409 <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPostShouldReturnConflict() throws Exception {
		var list = new ArrayList<Ausleihe>();
		list.add(testAusleihe);
		Mockito.when(mediumRepository.findById(id)).thenReturn(Optional.of(testMedium));
		Mockito.when(kundeRepository.findById(id)).thenReturn(Optional.of(testKunde));
		Mockito.when(ausleiheRepository.findByMediumId(id)).thenReturn(list);
		Mockito.when(ausleiheRepository.save(testAusleihe)).thenReturn(testAusleihe);

		mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json")
				.content(objectMapper.writeValueAsString(testAusleihe)))
				.andExpect(MockMvcResultMatchers.status().is(409))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link AusleiheController#updateAusleihe(Ausleihe, Long)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Aktualisieren einer Ausleihe anhand der Id <br>
	 * Erwartet: Statuscode 200 und aktualisierte Ausleihe <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPutShouldReturnOK() throws Exception {
		var list = new ArrayList<Ausleihe>();
		list.add(testAusleihe);
		Mockito.when(ausleiheRepository.findById(id)).thenReturn(Optional.of(testAusleihe));
		Mockito.when(ausleiheRepository.save(testAusleihe)).thenReturn(testAusleihe);

		var json = objectMapper.writeValueAsString(testAusleihe);
		mockMvc.perform(
				MockMvcRequestBuilders.put(END_POINT_PATH + "/" + id).contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(json))
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Test für {@link AusleiheController#deleteKunde(Long)} <br>
	 * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
	 * Testet: Löschen einer Ausleihe anhand der Medium-Id <br>
	 * Erwartet: Statuscode 200 <br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteShouldReturnOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" + id))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andDo(MockMvcResultHandlers.print());
	}

}
