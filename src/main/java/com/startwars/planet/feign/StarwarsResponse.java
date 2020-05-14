package com.startwars.planet.feign;

import java.util.Collection;

public class StarwarsResponse {

    private Collection<String> films;

    public Collection<String> getFilms(){
        return films;
    }

    public void setFilms(Collection<String> films) {
        this.films = films;
    }
}
