package com.castle.fortress.admin.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LocationUtil {
    /**
     * 地球半径,单位千米
     */
    private static final double EARTH_RADIUS = 6378.137;


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @param lat1 第一个纬度
     * @param lng1 第一个经度
     * @param lat2 第二个纬度
     * @param lng2 第二个经度
     * @return 两个经纬度的距离 单位千米
     */
        public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
            double radLat1 = rad(lat1);
            double radLat2 = rad(lat2);
            double a = radLat1 - radLat2;
            double b = rad(lng1) - rad(lng2);

            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
            s = s * EARTH_RADIUS;
            s = new BigDecimal(s).setScale(3, RoundingMode.HALF_UP).doubleValue();
            return s;
        }


}
