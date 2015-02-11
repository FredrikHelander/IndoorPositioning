package com.positioning.indoor.indoorpositioning;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hampus on 2015-02-10.
 */
public class ReferencePoint {
    private String coordX;
    private String coordY;
    private Map<String, String> fingerprint;

    public ReferencePoint(String coordX, String coordY, Map<String, String> fingerprint){
        this.coordX = coordX;
        this.coordY = coordY;
        this.fingerprint = fingerprint;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("x", coordX);
            obj.put("y", coordY);

            Set<String> aps = fingerprint.keySet();
            for (String ap : aps) {
                    obj.put(ap, fingerprint.get(ap));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(" + coordX + "," + coordY + "): ");
        Set<String> aps = fingerprint.keySet();
        for (String ap : aps) {
            sb.append(ap + " " + fingerprint.get(ap) + ", ");
        }
        return sb.toString();
    }

    //CREATE JSON OBJECT?
}
