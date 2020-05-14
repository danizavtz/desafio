package com.startwars.planet.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="swapi",
             url ="https://swapi.dev",
             configuration=FeignHandlerConfiguration.class)
public interface SwapiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/planets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    StarwarsResponse getFilms(@PathVariable("id")String id);

}
