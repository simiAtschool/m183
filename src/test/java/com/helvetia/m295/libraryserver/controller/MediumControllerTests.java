package com.helvetia.m295.libraryserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helvetia.m295.libraryserver.common.Medium;
import com.helvetia.m295.libraryserver.model.MediumRepository;
import com.helvetia.m295.libraryserver.service.AusleiheController;
import com.helvetia.m295.libraryserver.service.MediumController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

/**
 * Klasse für Testfälle der Klasse {@link MediumController}. <br>
 * Berechtigungen müssen bei den GET-Methoden nicht getestet werden, da die Rolle "USER" für diese Methoden verwendet wird und "USER" weniger Rechte hat, als "ADMIN".
 *
 * @author Simon Fäs
 * @version 1.0.0
 * @see MediumController
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MediumControllerTests {

    private static final String END_POINT_PATH = "/medium";
    private static final Long id = 1L;

    private Medium testMedium;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MediumRepository mediumRepository;

    /**
     * Constructor, um Test-Objekte zu initialisieren
     */
    public MediumControllerTests() {
        this.testMedium = new Medium(id, "Lord of the Rings", "J.R.R Tolkien", "Fantasy", (short) 13, 9803478347812L,
                "A1");
    }

    /**
     * Test für {@link MediumController#getMediumById(Long)} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Abrufen eines Mediums anhand seiner ID <br>
     * Erwartet: Statuscode 200 und ein Medium <br>
     *
     * @throws Exception Wenn ein Fehler beim Testen auftritt.
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetIdShouldReturnOK() throws Exception {

        Mockito.when(mediumRepository.findById(id)).thenReturn(Optional.of(testMedium));

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/" + id).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testMedium)))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link AusleiheController#getAusleiheById(Long)} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Abrufen eines nicht existenten Mediums anhand seiner ID <br>
     * Erwartet: Statuscode 404 <br>
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetIdShouldReturnNotFound() throws Exception {

        Mockito.when(mediumRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/" + id).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(404)).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link MediumController#getMedienByTitel(String)} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Abrufen von Medien anhand deren Titel <br>
     * Erwartet: Statuscode 200 und eine Liste von Medien <br>
     *
     * @throws Exception Wenn ein Fehler beim Testen auftritt.
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetTitelShouldReturnOK() throws Exception {

        Mockito.when(mediumRepository.findByTitel(testMedium.getTitel())).thenReturn(List.of(testMedium));

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/titel/" + testMedium.getTitel())
                        .contentType("application/json")).andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(testMedium))))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link MediumController#getAllMedien()} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Abrufen aller Medien <br>
     * Erwartet: Statuscode 200 und eine Liste von Medien <br>
     *
     * @throws Exception Wenn ein Fehler beim Testen auftritt.
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetAllShouldReturnOK() throws Exception {

        Mockito.when(mediumRepository.findAll()).thenReturn(List.of(testMedium));

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(testMedium))))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link MediumController#addMedium(Medium)} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Hinzufügen eines neuen Mediums <br>
     * Erwartet: Statuscode 200 <br>
     *
     * @throws Exception Wenn ein Fehler beim Testen auftritt.
     */
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testPostShouldReturnOK() throws Exception {

        Mockito.when(mediumRepository.save(testMedium)).thenReturn(testMedium);

        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json")
                        .content(objectMapper.writeValueAsString(testMedium))).andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link MediumController#updateMedium(Medium, Long)} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Aktualisieren eines Mediums <br>
     * Erwartet: Statuscode 200 und aktualisierter Kunde <br>
     *
     * @throws Exception Wenn ein Fehler beim Testen auftritt.
     */
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testPutShouldReturnOK() throws Exception {

        Mockito.when(mediumRepository.findById(id)).thenReturn(Optional.of(testMedium));
        Mockito.when(mediumRepository.save(testMedium)).thenReturn(testMedium);

        var json = objectMapper.writeValueAsString(testMedium);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(END_POINT_PATH + "/" + id).contentType("application/json").content(json))
                .andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.content().json(json))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link MediumController#deleteMedium(Long)} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Löschen eines Kunden anhand der ID <br>
     * Erwartet: Statuscode 200 <br>
     *
     * @throws Exception Wenn ein Fehler beim Testen auftritt.
     */
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteShouldReturnOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" + id).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Berechtigung von Rolle "User" bei der Ressource "Medium" bei POST
     * Requests <br>
     * Erwartet: Statuscode 403 <br>
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testPostWithRoleUser() throws Exception {

        Mockito.when(mediumRepository.save(testMedium)).thenReturn(testMedium);

        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json")
                        .content(objectMapper.writeValueAsString(testMedium))).andExpect(MockMvcResultMatchers.status().is(403))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Berechtigung von Rolle "User" bei der Ressource "Medium" bei PUT
     * Requests <br>
     * Erwartet: Statuscode 403 <br>
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testPutWithRoleUser() throws Exception {

        Mockito.when(mediumRepository.findById(id)).thenReturn(Optional.of(testMedium));
        Mockito.when(mediumRepository.save(testMedium)).thenReturn(testMedium);

        var json = objectMapper.writeValueAsString(testMedium);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(END_POINT_PATH + "/" + id).contentType("application/json").content(json))
                .andExpect(MockMvcResultMatchers.status().is(403))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Berechtigung von Rolle "User" bei der Ressource "Medium" bei DELETE
     * Requests <br>
     * Erwartet: Statuscode 403 <br>
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" + id).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(403)).andDo(MockMvcResultHandlers.print());
    }

}
