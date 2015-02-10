package com.positioning.indoor.indoorpositioning;

/**
 * Created by fredrik on 2015-02-10.
 */
public class Filter {
    private double[] sample;
    private double[] state;
    public Filter(double[] sample) {
        this.sample = sample;
    }

}
