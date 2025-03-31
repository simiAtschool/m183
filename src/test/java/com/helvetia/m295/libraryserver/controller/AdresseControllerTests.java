package com.helvetia.m295.libraryserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helvetia.m295.libraryserver.common.Adresse;
import com.helvetia.m295.libraryserver.common.Kunde;
import com.helvetia.m295.libraryserver.model.AdresseRepository;
import com.helvetia.m295.libraryserver.model.KundeRepository;
import com.helvetia.m295.libraryserver.service.AdresseController;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasse für Testfälle der Klasse {@link AdresseController}
 *
 * @author Simon Fäs
 * @version 1.0.0
 * @see AdresseController
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class AdresseControllerTests {

    private static final String END_POINT_PATH = "/adresse";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdresseRepository adresseRepository;

    @MockBean
    private KundeRepository kundeRepository;

    /**
     * Test für {@link AdresseController#getAdressenByZip(String)} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Abrufen von Adressen anhand des ZIP-Codes <br>
     * Erwartet: Statuscode 200 und eine Liste an Adressen<br>
     *
     * @throws Exception
     */
    @Test
    public void testGetByZipShouldReturnOK() throws Exception {
        String zip = "8008";
        Adresse adresse = new Adresse(1L, "Zürcherstrasse 1", "Zürich", zip);
        Mockito.when(adresseRepository.findByZip(zip)).thenReturn(List.of(adresse));

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/zip/" + zip).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(adresse))))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link AdresseController#getAdressenByAdresse(String)} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Abrufen von Adressen anhand der Strasse <br>
     * Erwartet: Statuscode 200 und eine Liste an Adressen <br>
     *
     * @throws Exception
     */
    @Test
    public void testGetByAdresseShouldReturnOK() throws Exception {
        String strAdresse = "Zürcherstrasse 1";
        Adresse adresse = new Adresse(1L, strAdresse, "Zürich", "8008");
        Mockito.when(adresseRepository.findByAdresse(strAdresse)).thenReturn(List.of(adresse));

        mockMvc.perform(
                        MockMvcRequestBuilders.get(END_POINT_PATH + "/strasse/" + strAdresse).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(adresse))))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link AdresseController#getAllAdressen()} <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Abrufen aller Adressen <br>
     * Erwartet: Statuscode 200 und eine Liste an Adressen <br>
     *
     * @throws Exception
     */
    @Test
    public void testGetAllShouldReturnOK() throws Exception {
        Adresse adresse = new Adresse(1L, "Zürcherstrasse 1", "Zürich", "8008");
        Mockito.when(adresseRepository.findAll()).thenReturn(List.of(adresse));

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(adresse))))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link AdresseController#deleteAdresse(Long)}. <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Löschen einer Adresse mit seiner Id <br>
     * Erwartet: Statuscode 200 <br>
     *
     * @throws Exception
     */
    @Test
    public void testDeleteByIdShouldReturnOK() throws Exception {
        Long id = 1L;
        Mockito.when(kundeRepository.findByAdresseId(id)).thenReturn(new ArrayList<Kunde>());

        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" + id).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test für {@link AdresseController#deleteAdresse(Long)}. <br>
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Handhabung eines Konflikta(Adresse wird noch referenziert). <br>
     * Erwartet: Statuscode 409 <br>
     *
     * @throws Exception
     */
    @Test
    public void testDeleteByIdShouldReturnConflict() throws Exception {
        Long id = 1L;
        var kunde = new Kunde(id, "Hans", "Meier", new Date(), new Adresse(1L, "Zürcherstrasse 1", "Zürich", "8008"),
                "hans.meier@hotmail.com");
        var list = new ArrayList<Kunde>();
        list.add(kunde);
        Mockito.when(kundeRepository.findByAdresseId(id)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" + id).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(409)).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Berechtigung von Rolle "User" bei der Ressource "Adresse" bei GET Requests <br>
     * Erwartet: Statuscode 403 <br>
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetWithRoleUser() throws Exception {
        String zip = "8008";
        Adresse adresse = new Adresse(1L, "Zürcherstrasse 1", "Zürich", zip);
        Mockito.when(adresseRepository.findByZip(zip)).thenReturn(List.of(adresse));

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/zip/" + zip).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(403))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * Braucht: Test-Objekte, welche im Constructor generiert werden. <br>
     * Testet: Berechtigung von Rolle "User" bei der Ressource "Adresse" bei DELETE Requests <br>
     * Erwartet: Statuscode 403 <br>
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteWithRoleUser() throws Exception {
        Long id = 1L;
        Mockito.when(kundeRepository.findByAdresseId(id)).thenReturn(new ArrayList<Kunde>());

        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" + id).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(403)).andDo(MockMvcResultHandlers.print());
    }

}