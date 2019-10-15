package com.hhdl.util;

import com.hhdl.model.TDestination;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DestinationUtil {
    /**
     * Logger for this class
     */

    private static final Double LATMAX = 30.791588;

    private static final Double LATMIN = 30.5716;


    private static final Double LNGMAX = 104.214456;

    private static final Double LNGMIN = 103.938417;

    public static List<TDestination> createDestinations() {
        List<TDestination> list = new ArrayList<TDestination>();
        for (int i = 0; i < 1000; i++) {
            TDestination tDestination = new TDestination();
            tDestination.setId(UUIDKey.getKey());
            BigDecimal lat = new BigDecimal(Double.toString(getRoundLatLng(LATMIN, LATMAX)));
            double latD = lat.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            tDestination.setLat(Double.toString(latD));
            BigDecimal lng = new BigDecimal(Double.toString(getRoundLatLng(LNGMIN, LNGMAX)));
            double lngD = lng.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            tDestination.setLng(Double.toString(lngD));
            list.add(tDestination);
        }
        return list;
    }

    private static double getRoundLatLng(double min, double max) {
        return RandomUtils.nextDouble(min, max);
    }
}
