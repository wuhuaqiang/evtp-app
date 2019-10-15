package com.hhdl.util;

public class PointUtil {
    //    private static int step = 0;
    public static Double STARTLNG = 103.940118;
    public static Double ENDLNG = 104.208116;
    public static Double STARTLAT = 30.579724;
    public static Double ENDLAT = 30.734065;
//    public static Double LAT = 0.1143;
//    public static Double LNG = 0.1879;

    public static String getPoint() {
//        step++;
        double randomLng = Math.random() / 10;
        double randomLat = Math.random() / 10;
        int step = (int) Math.random() * 10;
        double lng = 0.0;
        double lat = 0.0;
        if (step % 2 == 0) {
            lng = STARTLNG + randomLng * 2.5;
            lat = ENDLAT - randomLat * 1.5;
        } else {
            lng = ENDLNG - randomLng * 2.5;
            lat = STARTLAT + randomLat * 1.5;
        }
        if (lng < STARTLNG) {
            lng += randomLng;
        }
        if (lng > ENDLNG) {
            lng -= randomLng;
        }
        if (lat < STARTLAT) {
            lat += randomLat;
        }
        if (lat > ENDLAT) {
            lat -= randomLat;
        }
        return String.format("%.6f", lng) + "," + String.format("%.6f", lat);
    }

    public static String getInfo() {
        return (ENDLNG - STARTLNG) + "---" + (ENDLAT - STARTLAT);
    }

    public static void main(String[] args) {
        System.out.println(getInfo());
        System.out.println(getPoint());
    }
}
