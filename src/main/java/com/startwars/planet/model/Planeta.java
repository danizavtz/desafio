package com.startwars.planet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "planet")
public class Planeta {

    @Id
    private String id;
    private String nome;
    private String clima;
    private String terreno;

    public String getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getClima() {
        return clima;
    }

    public String getTerreno() {
        return terreno;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

}
