package org.web3.services.checker.plot;

import jakarta.enterprise.context.ApplicationScoped;
import org.web3.services.checker.plot.utils.PlotQuarters;
import org.web3.services.checker.plot.utils.PlotUtils;

@ApplicationScoped
public class Checker implements CheckerFunction{
    public boolean check(final float x, final float y, final float r) {
        final PlotQuarters quarter = PlotUtils.getQuarter(x, y);

        if (quarter == PlotQuarters.FIRST_QUADRANT) return firstQuarterTester(x, y, r);
        else if (quarter == PlotQuarters.SECOND_QUADRANT) return secondQuarterTester(x, y, r);
        else if (quarter == PlotQuarters.THIRD_QUADRANT) return thirdQuarterTester(x, y, r);
        return forthQuarterTester(x, y, r);
    }

    private boolean firstQuarterTester(final float x, final float y, final float r) {
        return x <= r * 0.5f && y <= r ;
    }

    private boolean secondQuarterTester(final float x, final float y, final float r) {
        return y >= (-r * 0.5f + 0.5f * x);
    }

    private boolean thirdQuarterTester(final float x, final float y,final float r) {
        return false;
    }

    private boolean forthQuarterTester(final float x, final float y, final float r) {
        return Math.sqrt(x * x + y * y) <= r * 0.5f;
    }
}