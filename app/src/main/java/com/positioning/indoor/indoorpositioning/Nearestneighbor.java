package com.positioning.indoor.indoorpositioning;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by fredrik on 2015-02-10.
 */
public class Nearestneighbor {
    public ArrayList<double[]> rp;

    public Nearestneighbor(ArrayList<double[]> rp) {
        this.rp = rp;
    }

    public int getLocation(double[] v) {
        double[] dist = new double[rp.size()];
        double[] temp = new double[rp.size()];
        for(int i = 0; i < rp.size(); i++) {
            dist[i] = Euclidean(v, rp.get(i));
            temp[i] = dist[i];
        }
        Arrays.sort(dist);
        //for(int j = 0; j < rp.size(); j++) {  //Hmm
        for(int j = 0; j < rp.size(); j++) {
            if(temp[j] == dist[0]) {
                return j;
            }
        }
        return -1;
    }

    public double Euclidean(double[] s, double[] v) {
        double distance = 0;
        for(int i = 0; i < 6; i++) {     //6 was size of sample
            distance += Math.pow((s[i]-v[i]), 2);
        }
        distance = Math.sqrt(distance);
        return distance;
    }
}
