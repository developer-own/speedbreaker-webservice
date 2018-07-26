package com.tomtom.devday.speedbumper.service;

import java.util.List;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.tomtom.devday.speedbumper.constant.SearchConstant;
import com.tomtom.devday.speedbumper.model.Coordinate;
import com.tomtom.devday.speedbumper.utils.SearchUtil;

public class SearchBuilder {

    public QueryBuilder buildRediusQuery(List<Coordinate> coordinates) {
        final BoolQueryBuilder parentBoolQuery = new BoolQueryBuilder();
        final ConstantScoreQueryBuilder constantScoreQuery = new ConstantScoreQueryBuilder(parentBoolQuery);
        parentBoolQuery.must(QueryBuilders.termQuery(SearchConstant.TYPE, SearchConstant.ROADELEMENT));

        final GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder(SearchConstant.BUMPERS_LOCATION);
        geoDistanceQueryBuilder.geoDistance(GeoDistance.PLANE);
        geoDistanceQueryBuilder.distance(SearchConstant.RADIUS, DistanceUnit.METERS);
        geoDistanceQueryBuilder.point(coordinates.get(0).getLat(), coordinates.get(0).getLon());
        final BoolQueryBuilder filterQueryBuilder = new BoolQueryBuilder();
        filterQueryBuilder.should(geoDistanceQueryBuilder);
        parentBoolQuery.filter(filterQueryBuilder);
        return constantScoreQuery;
    }

    public QueryBuilder buildExactBoundingBoxQuery(List<Coordinate> coordinates) {
        final BoolQueryBuilder parentBoolQuery = new BoolQueryBuilder();
        final ConstantScoreQueryBuilder constantScoreQuery = new ConstantScoreQueryBuilder(parentBoolQuery);
        parentBoolQuery.must(QueryBuilders.termQuery(SearchConstant.TYPE, SearchConstant.ROADELEMENT));

        final GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder =
            new GeoBoundingBoxQueryBuilder(SearchConstant.BUMPERS_LOCATION);

        final GeoPoint topLeftGeoPoint = new GeoPoint();
        topLeftGeoPoint.reset(coordinates.get(1).getLat(), coordinates.get(1).getLon());

        final GeoPoint bottomRightGeoPoint = new GeoPoint();
        bottomRightGeoPoint.reset(coordinates.get(0).getLat(), coordinates.get(0).getLon());

        SearchUtil.swapTopAndBottom(topLeftGeoPoint, bottomRightGeoPoint);

        geoBoundingBoxQueryBuilder.setCorners(topLeftGeoPoint, bottomRightGeoPoint);

        final BoolQueryBuilder bboxBuilder = new BoolQueryBuilder();
        bboxBuilder.should(geoBoundingBoxQueryBuilder);
        parentBoolQuery.filter(bboxBuilder);

        return constantScoreQuery;

    }
}
