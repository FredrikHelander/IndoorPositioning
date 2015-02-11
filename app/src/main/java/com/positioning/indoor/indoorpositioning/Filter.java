package com.positioning.indoor.indoorpositioning;

/**
 * Created by fredrik on 2015-02-10.
 */
        /*
        βi,k Weight of ith calibration point
        a¯i Mean of radio map RSSI values
        fk−1 State transition function
        Fk−1 Jacobian of the state transition function
        pi Coordinates of the ith calibration point
        Pai Covariance of radio map RSSI values
        Ppi Covariance of uniform distribution in cell i
        Qk−1 State noise covariance matrix
        yk Measurement vector

        1) Start with the initial estimate xˆ0 = E (x0) and the covariance
           P0 = V (x0) of the estimation error. Set k = 1.
        */
public class Filter {
    private double[] sample;
    private double[] state;
    private double[][] transitionmatrix;
    private double[][] controlmatrix;
    private double[][] noisematrix;
    private double[][] H;
    private double[][] coPrediction;

    public Filter(double[] sample) {
        this.sample = sample;
        transitionmatrix = new double[][] { {1, 0, 1, 0},
                                            {0, 1, 0, 1},
                                            {0, 0, 1, 0},
                                            {0, 0, 0, 1} };

        noisematrix = new double[][] { {0.1, 0, 0, 0},
                                       {0, 0.1, 0, 0},
                                       {0, 0, 0.1, 0},
                                       {0, 0, 0, 0.1} };

        H = new double[][] { {1, 0, 0, 0},
                             {0, 1, 0, 0} };

        coPrediction = new double[][] { {10, 0, 0, 0, 0, 0},
                                        {0, 10, 0, 0, 0, 0},
                                        {0, 0, 10, 0, 0, 0},
                                        {0, 0, 0, 10, 0, 0},
                                        {0, 0, 0, 0, 10, 0},
                                        {0, 0, 0, 0, 0, 10} };

        
    }


}
