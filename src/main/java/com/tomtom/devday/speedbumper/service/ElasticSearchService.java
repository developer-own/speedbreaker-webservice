package com.tomtom.devday.speedbumper.service;

import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.tomtom.devday.speedbumper.constant.SearchConstant;
import com.tomtom.devday.speedbumper.exception.SearchException;
import com.tomtom.devday.speedbumper.model.Coordinate;
import com.tomtom.devday.speedbumper.model.GeoResponse;
import com.tomtom.devday.speedbumper.model.SearchResult;

@Component
public class ElasticSearchService {

    @Value("${esIndexName}")
    private String indexName;

    @Autowired
    private Gson gson;

    @Autowired
    private RestHighLevelClient client;
    private final SearchBuilder searchBuilder;

    public ElasticSearchService() {
        searchBuilder = new SearchBuilder();
    }

    public GeoResponse getSearch(List<Coordinate> coordinates) throws SearchException {
        final QueryBuilder theQuery = searchBuilder.buildRediusQuery(coordinates);
        final SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types("feature");
        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(theQuery);
        sourceBuilder.size(SearchConstant.RESPONSE_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        System.out.println(searchRequest);
        try {
            searchResponse = client.search(searchRequest);
            debugLog(coordinates, searchResponse);
            return getBumpers(searchResponse);

        } catch (final Exception ex) {
            throw new SearchException(ex.getMessage());
        }
    }

    private void debugLog(List<Coordinate> coordinates, SearchResponse searchResponse) {
        final StringBuilder bulder = new StringBuilder();
        if (searchResponse != null) {
            bulder.append("georesponse : " + searchResponse.getHits().getHits().length);
            bulder.append("\n");
        } else {
            bulder.append("georesponse : " + searchResponse);
        }

        for (final Coordinate coordinate : coordinates) {
            bulder.append(coordinate.getLat() + " " + coordinate.getLon() + " ,");
        }
        System.out.println(bulder);
    }

    private GeoResponse getBumpers(SearchResponse resp) throws JSONException {
        final GeoResponse geoResponse = new GeoResponse();
        final int hitCount = resp.getHits().getHits().length;
        if (resp != null && hitCount > 0) {
            for (int i = 0; i < hitCount; i++) {
                final String resultSourceJson = resp.getHits().getHits()[i].getSourceAsString();
                final String id = resp.getHits().getHits()[i].getId();
                final SearchResult bumper = gson.fromJson(resultSourceJson, SearchResult.class);
                bumper.setId(id);
                geoResponse.getGeoResponse().add(bumper);
            }
        }
        return geoResponse;
    }

}
