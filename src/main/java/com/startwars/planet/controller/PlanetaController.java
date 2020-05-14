package com.startwars.planet.controller;

import com.startwars.planet.feign.StarwarsResponse;
import com.startwars.planet.feign.SwapiClient;
import com.startwars.planet.model.Planeta;
import com.startwars.planet.repository.PlanetaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/planetas")
public class PlanetaController {

    private SwapiClient starwarsapi;
    private PlanetaRepository planetaRepository;

    public PlanetaController(PlanetaRepository planetaRepository, SwapiClient starwarsapi) {
        this.planetaRepository = planetaRepository;
        this.starwarsapi = starwarsapi;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/filmes/{id}/", method = RequestMethod.GET)
    public StarwarsResponse getFilmes(@PathVariable String id) {
        return starwarsapi.getFilms(id);
    }

    @GetMapping("/")
    public List<Planeta> getAll(){
        return planetaRepository.findAll();
    }

    @PostMapping("/")
    public Planeta adicionar(@RequestBody Planeta planeta) {
        return planetaRepository.save(planeta);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Planeta> atualizar(@PathVariable("id") String id, @RequestBody Planeta planeta) {
        return planetaRepository.findById(id)
                .map(documento -> {
                    documento.setNome(planeta.getNome());
                    documento.setClima(planeta.getClima());
                    documento.setTerreno((planeta.getTerreno()));
                    Planeta atualizado = planetaRepository.save(documento);
                    return ResponseEntity.ok().body(atualizado);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity delete(@PathVariable String id) {
        return planetaRepository.findById(id)
                .map(documento -> {
                    planetaRepository.deleteById(documento.getId());
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planeta> getById(@PathVariable("id") String id) {
        return planetaRepository.findById(id)
                .map(document -> ResponseEntity.ok().body(document))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public List<Planeta> getByName(@PathVariable("nome")String nome) {
        return planetaRepository.findByNome(nome);
    }


}
