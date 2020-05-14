package com.startwars.planet.repository;

import com.startwars.planet.model.Planeta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, String> {

    public List<Planeta> findByNome(String nome);
}
