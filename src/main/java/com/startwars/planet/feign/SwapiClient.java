package com.startwars.planet.feign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Headers("Accept: application/json")
@FeignClient(value="swapi",
             url="https://swapi.dev",
             configuration=FeignHandlerConfiguration.class)
public interface SwapiClient {
    @Headers("Content-Type: application/json")

    @RequestMapping("/api/planets/{id}")
    StarwarsResponse getFilms(@PathVariable("id")String id);

}
