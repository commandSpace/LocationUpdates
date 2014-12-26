package com.notify;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * Created by ramana on 22/12/14.
 */
public class LocationUpdateList extends Activity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {
    ListView listView;
    ArrayList<String> location_updates;
    ArrayAdapter<String> adapter;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private LocationRequest mLocationRequest;
    TextView header ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_update_list);
        listView = (ListView) findViewById(R.id.list);
        location_updates = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.test_list_item,location_updates);

        header = (TextView) getLayoutInflater().inflate(android.R.layout.test_list_item,null);



        listView.setAdapter(adapter);
        listView.addHeaderView(header);
        adapter.notifyDataSetChanged();

        header.setText("dumb");

        buildGoogleApiClient();
        createLocationRequest();

    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.getExpirationTime();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null){
            String cur_loc = "Current Loc \n"+getString(mLastLocation.getLatitude())+" , "+getString(mLastLocation.getLongitude());
            header.setText(cur_loc);

        }
        startLocationUpdates();
    }

    public String getString(double d){
        return String.valueOf(d);
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        addLastLocationToList();
    }

    private void addLastLocationToList() {
        LocationObject locationObject = new LocationObject(mLastLocation);
        location_updates.add(locationObject.giveLatLngString());
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,location_updates);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    protected void startLocationUpdates(){
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }
    protected void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
    }

}