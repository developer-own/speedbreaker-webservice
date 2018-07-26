package com.tomtom.devday.speedbumper.utils;

import org.elasticsearch.common.geo.GeoPoint;

public class SearchUtil {

    public static void swapTopAndBottom(GeoPoint topLeftGeoPoint, GeoPoint bottomRightGeoPoint) {

        if (!isValid(topLeftGeoPoint, bottomRightGeoPoint)) {
            final GeoPoint temp = new GeoPoint();
            temp.reset(topLeftGeoPoint.getLat(), topLeftGeoPoint.getLon());
            topLeftGeoPoint.reset(bottomRightGeoPoint.getLat(), bottomRightGeoPoint.getLon());
            bottomRightGeoPoint.reset(temp.getLat(), temp.getLon());
        }

    }

    private static boolean isValid(GeoPoint topLeftGeoPoint, GeoPoint bottomRightGeoPoint) {
        boolean isValid = true;
        if (topLeftGeoPoint.getLat() < bottomRightGeoPoint.getLat()) {
            isValid = false;
        } else if (topLeftGeoPoint.getLat() == bottomRightGeoPoint.getLat()) {
            isValid = false;
        } else if (topLeftGeoPoint.getLon() == bottomRightGeoPoint.getLon()) {
            isValid = false;
        }

        return isValid;
    }
}
