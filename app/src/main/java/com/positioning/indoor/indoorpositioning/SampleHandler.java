package com.positioning.indoor.indoorpositioning;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hampus on 2015-02-10.
 */
public class SampleHandler {
    private Map<String, ArrayList<Integer>> samples;
    private ArrayList<String> devices;

    public SampleHandler() {
        samples = new HashMap<String, ArrayList<Integer>>();
        devices = new ArrayList<String>();
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
        if (!devices.contains(key))
            devices.add(key);
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

    public ReferencePoint getReferencePoint(String coordX, String coordY) {
        Map<String, String> fingerprint = new HashMap<String, String>();
        Set<String> keys = samples.keySet();
        for (String key : keys) {
            int sum = 0;
            for (int sample : samples.get(key)) {
                sum += sample;
            }
            String average = new DecimalFormat("#.00").format((double) sum / (double) samples.get(key).size());
            fingerprint.put(key, average);
        }
        samples.clear();
        return new ReferencePoint(coordX, coordY, fingerprint);
    }

/*    public void saveFingerprintsToFile() {

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file1 = new File(path, "fingerprints.csv");
        FileOutputStream outputStream;

        try {
            FileOutputStream stream = new FileOutputStream(file1, true);
            stream.write(printFingerprints());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public byte[] printFingerprints() {
        StringBuilder sb = new StringBuilder();
        Collections.sort(devices);

        for (String device : devices) {

        }
        return sb.toString().getBytes();
    }*/
}
