package com.positioning.indoor.indoorpositioning;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hampus on 2015-02-10.
 */
public class SampleHandler {
    private Map<String, ArrayList<Integer>> samples;
    private Map<String, ArrayList<String>> fingerprints;

    public SampleHandler() {
        samples = new HashMap<String, ArrayList<Integer>>();
        fingerprints = new HashMap<String, ArrayList<String>>();
    }

    public void clear() {
        samples.clear();
    }

    public boolean containsKey(String key) {
        return samples.containsKey(key);
    }

    public Object get(String key) {
        return samples.get(key);
    }

    public boolean isEmpty() {
        return samples.isEmpty();
    }

    public Set keySet() {
        return samples.keySet();
    }

    public Object put(String key, Integer value) {
        if (!samples.containsKey(key))
            samples.put(key, new ArrayList<Integer>());
        return samples.get(key).add(value);
    }

    public Object remove(Object key) {
        return samples.remove(key);
    }

    public int size() {
        return samples.size();
    }

    public Collection values() {
        return samples.values();
    }

    public void saveSample() {
        Set<String> keys = samples.keySet();
        for (String key : keys) {
            int sum = 0;
            for (int sample : samples.get(key)) {
                sum += sample;
            }
            String average = new DecimalFormat("#.00").format((double) sum / (double) samples.get(key).size());
            if (!fingerprints.containsKey(key))
                fingerprints.put(key, new ArrayList<String>(Arrays.asList(average)));
            fingerprints.get(key).add(average);
        }
    }

    public Set<Map.Entry<String, ArrayList<String>>> getFingerprints() {
        return fingerprints.entrySet();
    }
}
