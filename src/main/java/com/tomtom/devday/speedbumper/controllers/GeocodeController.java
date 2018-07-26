package com.tomtom.devday.speedbumper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tomtom.devday.speedbumper.exception.SearchException;
import com.tomtom.devday.speedbumper.model.Bumpers;
import com.tomtom.devday.speedbumper.model.GeoResponse;
import com.tomtom.devday.speedbumper.service.GeocoderService;

@RestController
@RequestMapping("/")
public class GeocodeController {

    @Autowired
    GeocoderService geocoderService;

    @RequestMapping(value = "/geocode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonProperty
    public GeoResponse processQuery(@RequestBody Bumpers bumpers) throws SearchException {
        System.out.println("Reached");
        return geocoderService.geocode(bumpers.getLocation());
    }

}
