package com.notify;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ramana on 23/12/14.
 */
public class LocationObject {
    Location location;
    String locationName;
    String velocity;
    String accuracy;
    String altitude;
    String provider;
    public LocationObject(Location location){
        this.location = location;
        locationName = "not set yet";
        velocity = String.valueOf(location.getSpeed());
        accuracy = String.valueOf(location.getAccuracy());
        altitude = String.valueOf(location.getAltitude());
        provider = location.getProvider();
    }

    public String giveLatLngString(){
        return "Location : "+String.valueOf(location.getLatitude())+" , "+String.valueOf(location.getLongitude())
                +addEndOfLine("Velocity", velocity)
                +addEndOfLine("Accuracy",accuracy)
                +addEndOfLine("Altitude",altitude)
                +addEndOfLine("Provider",provider)
                +addEndOfLine("Place Name",locationName)
                +addEndOfLine("Time : ",timeStamp());
    }
    private String addEndOfLine(String tag,String val){
        if(val == null) val = "not set";
        return "\n "+tag+" : "+val;
    }
    private String timeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString=sdf.format(new Date());

        return dateString;
    }
}
