package com.tomtom.devday.speedbumper.model;

import java.util.ArrayList;
import java.util.List;

public class GeoResponse {

    private List<SearchResult> geoResponse;

    public List<SearchResult> getGeoResponse() {
        if (geoResponse == null) {
            geoResponse = new ArrayList();
        }
        return geoResponse;
    }

    public void setGeoResponse(List<SearchResult> geoResponse) {
        this.geoResponse = geoResponse;
    }

}
