package sk.epholl.dissim.sem3.util;

import OSPRNG.RNG;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

/**
 * Created by Tomáš on 22.05.2016.
 */
public class Rand {

    private final RNG<? extends Number> ossimRng;
    private final AbstractRealDistribution apacheRng;
    private final double offset;
    private final double multiplier;

    public Rand(RNG<? extends Number> ossimRng) {
        this.ossimRng = ossimRng;
        apacheRng = null;
        offset = 0D;
        multiplier = 1D;
    }

    public Rand(AbstractRealDistribution apacheRng) {
        this.apacheRng = apacheRng;
        ossimRng = null;
        offset = 0D;
        multiplier = 1D;
    }

    public Rand(RNG<? extends Number> ossimRng, double offset, double multiplier) {
        this.ossimRng = ossimRng;
        this.apacheRng = null;
        this.offset = offset;
        this.multiplier = multiplier;
    }

    public Rand(AbstractRealDistribution apacheRng, double offset, double multiplier) {
        this.ossimRng = null;
        this.apacheRng = apacheRng;
        this.offset = offset;
        this.multiplier = multiplier;
    }

    public double sample() {
        double sample;
        if (ossimRng != null) {
            sample = ossimRng.sample().doubleValue();
        } else {
            sample = apacheRng.sample();
        }
        return offset + (sample * multiplier);
    }

    public RNG<? extends Number> getOssimRng() {
        return ossimRng;
    }

    public AbstractRealDistribution getApacheRng() {
        return apacheRng;
    }
}
