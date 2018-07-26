package com.tomtom.devday.speedbumper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tomtom.devday.speedbumper.exception.SearchException;
import com.tomtom.devday.speedbumper.model.Coordinate;
import com.tomtom.devday.speedbumper.model.GeoResponse;

@Component
public class GeocoderService {

    @Autowired
    ElasticSearchService elasticSearchService;

    public GeoResponse geocode(List<Coordinate> coordinates) throws SearchException {
        return elasticSearchService.getSearch(coordinates);

    }

}
