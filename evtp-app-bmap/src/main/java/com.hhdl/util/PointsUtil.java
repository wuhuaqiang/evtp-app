package com.hhdl.util;

import com.hhdl.model.MapPoint;

import java.util.ArrayList;
import java.util.List;

public class PointsUtil {
    public static List<MapPoint> getDetailPints(List<MapPoint> mapPoints, double stepLat, double stepLng) {
        List<MapPoint> mapPointList = new ArrayList<MapPoint>();
        double latNew = 0;
        double lngNew = 0;
        for (int i = 0; i < (mapPoints.size() - 1); i++) {
            double latF = mapPoints.get(i).getLat();
            double lngF = mapPoints.get(i).getLng();
            double latE = mapPoints.get(i + 1).getLat();
            double lngE = mapPoints.get(i + 1).getLng();
            double v = Math.abs(latF - latE) / stepLat;
            double dLat = Math.round(v);
            double dLng = 0;
            if (i == 0) {
                mapPointList.add(mapPoints.get(i));
            }
            if (dLat > 0) {
                dLng = Math.abs(lngF - lngE) / dLat;
            }
            if (Math.abs(latF - latE) < 0.000002 && Math.abs(lngF - lngE) > 0.0001) {
                dLng = Math.round(Math.abs(lngF - lngE) / stepLng);
                for (int j = 0; j < dLng - 1; j++) {
                    latNew = latF;
                    if (lngF - lngE < 0) {
                        lngNew = (double) Math.round((lngF + j * stepLng) * 1000000) / 1000000;
                    } else {
                        lngNew = (double) Math.round((lngF - j * stepLng) * 1000000) / 1000000;
                    }
                    MapPoint mapPoint = new MapPoint();
                    mapPoint.setLng(lngNew);
                    mapPoint.setLat(latNew);
                    mapPointList.add(mapPoint);
                }
            } else {
                for (int m = 0; m < dLat - 1; m++) {
                    if (latF - latE < 0) {
                        latNew = (double) Math.round((latF + m * stepLat) * 1000000) / 1000000;
                    } else {
                        latNew = (double) Math.round((latF - m * stepLat) * 1000000) / 1000000;
                    }
                    if (lngF - lngE < 0) {
                        lngNew = (double) Math.round((lngF + m * dLng) * 1000000) / 1000000;
                    } else {
                        lngNew = (double) Math.round((lngF - m * dLng) * 1000000) / 1000000;
                    }
                    MapPoint mapPoint = new MapPoint();
                    mapPoint.setLng(lngNew);
                    mapPoint.setLat(latNew);
                    mapPointList.add(mapPoint);

                }
            }
        }
        return mapPointList;
    }
}
/*
//获得详细线路点数组
function getDetailPints(points, stepLat, stepLng) {
const newPoints = new Array();
    let latNew = 0;
    let lngNew = 0;
    for (let i = 0; i < (points.length - 1); i++) {
    const latF = points[i].lng;
    const lngF = points[i].lat;
    const latE = points[i + 1].lng;
    const lngE = points[i + 1].lat;
    const dLat = Math.round(Math.abs(latF - latE) / stepLat, 0);
        let dLng = 0;
        if (!i) {
            newPoints.push(points[i]);
        }
        if (dLat) {
            dLng = Math.abs(lngF - lngE) / dLat;
        }
        if (Math.abs(latF - latE) < 0.000002 && Math.abs(lngF - lngE) > 0.0001) {
            dLng = Math.round(Math.abs(lngF - lngE) / stepLng, 0);
            for (let j = 0; j < dLng - 1; j++) {
                latNew = latF;
                if (lngF - lngE < 0) {
                    lngNew = Math.round((lngF + j * stepLng) * 1000000) / 1000000;
                } else {
                    lngNew = Math.round((lngF - j * stepLng) * 1000000) / 1000000;
                }
            const newPoint = new BMap.Point(latNew, lngNew);
                newPoints.push(newPoint);
            }
        } else {
            for (let m = 0; m < dLat - 1; m++) {
                if (latF - latE < 0) {
                    latNew = Math.round((latF + m * stepLat) * 1000000) / 1000000;
                } else {
                    latNew = Math.round((latF - m * stepLat) * 1000000) / 1000000;
                }
                if (lngF - lngE < 0) {
                    lngNew = Math.round((lngF + m * dLng) * 1000000) / 1000000;
                } else {
                    lngNew = Math.round((lngF - m * dLng) * 1000000) / 1000000;
                }
            const newPoint = new BMap.Point(latNew, lngNew);
                newPoints.push(newPoint);

            }
        }

        newPoints.push(points[i + 1]);
    }
    return newPoints;
}*/
