package com.startwars.planet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startwars.planet.controller.PlanetaController;
import com.startwars.planet.model.Planeta;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplication {

    @Autowired
    private PlanetaController planetaController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        //limpar efeitos colaterais
        List<Planeta> planetas = planetaController.getAll();
        for (Planeta planeta : planetas) {
            planetaController.delete(planeta.getId());
        }

        Planeta p = new Planeta();
        p.setNome("eu");
        p.setClima("cli");
        p.setTerreno("terr");
        mockMvc.perform(post("/planetas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(p)))
                .andExpect(status().isCreated());
    }
    @Test
    public void shouldReturnAllPlanetsRoute() throws Exception {
        mockMvc.perform(get("/planetas/")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]nome", Matchers.is("eu")))
                .andExpect(jsonPath("$.[0]clima", Matchers.is("cli")))
                .andExpect(jsonPath("$.[0]terreno", Matchers.is("terr")));
    }

    @Test
    public void shouldReturn404OrRouteError() throws Exception {
        mockMvc.perform(get("/test")).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn404InGetById() throws Exception {
        mockMvc.perform(get("/planetas/0")).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn404InPutNotFound()throws Exception {
        mockMvc.perform(put("/planetas/0")).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn200InNameNotFound() throws Exception {
        mockMvc.perform(get("/planetas/nome/cafe"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldReturn404InDeleteNotFound()throws Exception {
        mockMvc.perform(delete("/planetas/0")).andExpect(status().is4xxClientError());
    }

    private static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shoulDoAPostWithSuccess() throws Exception {
        Planeta p = new Planeta();
        p.setNome("eu");
        p.setClima("cli");
        p.setTerreno("terr");
        mockMvc.perform(post("/planetas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(p)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetElementWithSuccess() throws Exception {
        Planeta p = planetaController.getAll().get(0);//first
        mockMvc.perform(get("/planetas/"+p.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shoulPutElementWithSuccess() throws Exception {
        Planeta p1 = new Planeta();
        p1.setNome("eu");
        p1.setClima("cli");
        p1.setTerreno("terr");
        Planeta p = planetaController.adicionar(p1);

        mockMvc.perform(get("/planetas/"+p.getId())
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("$.nome", Matchers.is("eu")))
                .andExpect(jsonPath("$.clima", Matchers.is("cli")))
                .andExpect(jsonPath("$.terreno", Matchers.is("terr")));
        Planeta pAtualizado = new Planeta();
        pAtualizado.setNome("Tatooine");
        pAtualizado.setTerreno("Deserto");
        pAtualizado.setClima("Seco");
        mockMvc.perform(put("/planetas/"+p.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(pAtualizado)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nome", Matchers.is("Tatooine")))
                .andExpect(jsonPath("$.clima", Matchers.is("Seco")))
                .andExpect(jsonPath("$.terreno", Matchers.is("Deserto")));
    }

    @Test
    public void testGetPlanetByName() throws Exception {
        Planeta p1 = new Planeta();
        p1.setNome("everdream");
        p1.setClima("clima");
        p1.setTerreno("terreno");
        planetaController.adicionar(p1);

        mockMvc.perform(get("/planetas/nome/"+p1.getNome())
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0]nome", Matchers.is("everdream")))
                .andExpect(jsonPath("$.[0]clima", Matchers.is("clima")))
                .andExpect(jsonPath("$.[0]terreno", Matchers.is("terreno")));
    }

    @Test
    public void testDeletePlanet() throws Exception {
        Planeta p = planetaController.getAll().get(0);
        mockMvc.perform(delete("/planetas/"+p.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}
