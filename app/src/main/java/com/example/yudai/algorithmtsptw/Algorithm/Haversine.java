package com.example.yudai.algorithmtsptw.Algorithm;

public class Haversine {
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public static double distance(String locate1,
                                  String locate2) {
        double startLat, startLong, endLat, endLong;

        String[] LatLong1 = locate1.split("\\,");
        String[] LatLong2 = locate2.split("\\,");

        startLat = Double.parseDouble(LatLong1[0]);
        startLong = Double.parseDouble(LatLong1[1]);

        endLat = Double.parseDouble(LatLong2[0]);
        endLong = Double.parseDouble(LatLong2[1]);

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c * 1.5; // <-- d
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public double travelTime(String locate1,
                             String locate2){
        double mDistance = distance(locate1, locate2);
        return ((double) Math.round((((mDistance/50)*60)*1.3) * 1) / 1);
    }

}
