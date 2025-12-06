package org.web3.services.checker.plot.utils;

public class PlotUtils {
    public static PlotQuarters getQuarter(final float x, final float y) {
        boolean isXGraterOrEqualsZero = x >= 0;
        boolean isYGraterOrEqualsZero = y >= 0;

        if (isXGraterOrEqualsZero && isYGraterOrEqualsZero) return PlotQuarters.FIRST_QUADRANT;
        else if (isXGraterOrEqualsZero && !isYGraterOrEqualsZero) return PlotQuarters.SECOND_QUADRANT;
        else if (!isXGraterOrEqualsZero && !isYGraterOrEqualsZero) return PlotQuarters.THIRD_QUADRANT;
        return PlotQuarters.FOURTH_QUADRANT;
    }
}
