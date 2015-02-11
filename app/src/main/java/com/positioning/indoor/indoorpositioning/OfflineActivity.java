package com.positioning.indoor.indoorpositioning;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hampus on 2015-02-10.
 */
public class OfflineActivity extends ActionBarActivity{
    private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);
    private static int NBR_OF_SAMPLES = 0;
    private static int sampleCount = 0;
    private ArrayList<ReferencePoint> referencePoints;

    private TextView tvNbrOfValues;
    private EditText coordX;
    private EditText coordY;
    private SeekBar sampleBar;

    private TextView tvCount;

    private BeaconManager beaconManager;
    private SampleHandler sampleHandler;

    public void startSampling(View view) {
        sampleBar.setEnabled(false);
        coordX.setEnabled(false);
        coordY.setEnabled(false);

        OfflineActivity.NBR_OF_SAMPLES = Integer.valueOf(tvNbrOfValues.getText().toString());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e("Beacon", "Cannot start ranging", e);
                }
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                for (Beacon b : beacons) {
                    sampleHandler.put(b.getMacAddress(), b.getRssi());
                    Log.d("BEACON", b.getMacAddress() + " " + b.getRssi());
                }
                sampleCount++;
                tvCount.setText(Integer.toString(sampleCount));
                if (sampleCount >= NBR_OF_SAMPLES) {
                    tvCount.setText(Integer.toString(0));
                    try {
                        sampleCount = 0;
                        beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    referencePoints.add(sampleHandler.getReferencePoint(coordX.getText().toString(), coordY.getText().toString()));
                    //SEND REFERENCEPOINT
                    enableLayout();
                }
            }
        });
    }

    public void enableLayout() {
        sampleBar.setEnabled(true);
        coordX.setEnabled(true);
        coordY.setEnabled(true);
    }

    private void setupEstimote() {
        beaconManager = new BeaconManager(getApplicationContext());

        if (!beaconManager.isBluetoothEnabled())
            Toast.makeText(getApplicationContext(), "Enable Bluetooth", Toast.LENGTH_SHORT).show();
    }



    private void setupLayout() {
        sampleBar = (SeekBar)findViewById(R.id.sampleBar);
        sampleBar.setProgress(0);
        sampleBar.incrementProgressBy(10);
        sampleBar.setMax(200);

        tvNbrOfValues = (TextView)findViewById(R.id.nbrOfSamples);
        tvNbrOfValues.setText(String.valueOf(0));

        coordX = (EditText)findViewById(R.id.coordX);
        coordY = (EditText)findViewById(R.id.coordY);

        sampleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10;
                progress = progress * 10;
                tvNbrOfValues.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        sampleHandler = new SampleHandler();
        referencePoints = new ArrayList<ReferencePoint>();
        tvCount = (TextView)findViewById(R.id.tvCount);
        setupLayout();
        setupEstimote();
    }

    @Override
    protected void onStop() {
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
        } catch (RemoteException e) {
            Log.e("Beacon", "Cannot stop but it does not matter now", e);
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e("Beacon", "Cannot start ranging", e);
                }
            }
        });
        super.onStart();
    }

    @Override
    protected void onPause() {
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
        } catch (RemoteException e) {
            Log.e("Beacon", "Cannot stop but it does not matter now", e);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e("Beacon", "Cannot start ranging", e);
                }
            }
        });
        super.onResume();
    }

    @Override
    protected void onRestart() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e("Beacon", "Cannot start ranging", e);
                }
            }
        });
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        beaconManager.disconnect();
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.action_online:
                intent = new Intent(this, OnlineActivity.class);
                startActivity(intent);
                break;
            case R.id.action_offline:
                intent = new Intent(this, OfflineActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
