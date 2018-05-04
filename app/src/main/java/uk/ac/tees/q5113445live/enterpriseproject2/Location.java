package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Luke on 27/04/2018.
 */

public class Location implements Serializable
{
    private String address;
    private String lat;
    private String lng;
    private HashMap<String, String> latlng;

//    Location()
//    {
//        houseNum = "";
//        postCode = "";
//        context = null;
//    }
    Location(Place p)
    {
        address = p.getAddress().toString();
        lat = Double.toString(p.getLatLng().latitude);
        lng = Double.toString(p.getLatLng().longitude);

    }
    Location(String a, String la, String ln)
    {
        address = a;
        lat = la;
        lng = ln;
    }
    Location(String a, HashMap laln)
    {
        address = a;
        latlng = laln;
        lat = latlng.get("latitude");
        lng = latlng.get("longitude");
    }

    public HashMap<String, String> getLatlng()
    {
        return latlng;
    }

    public void setLatlng(HashMap<String, String> latlng) {
        this.latlng = latlng;
    }

    Location() {
        address = "";
        lat = "";

        lng = "";
    }

//    public String getCity(String postcode)
//    {
//        String c = null;
//        Geocoder gps = new Geocoder(context, Locale.getDefault());
//        if (gps.isPresent()) {
//            try {
//                List<Address> list = gps.getFromLocationName("NE8 2TL", 10);
//                Address address = list.get(0);
//                double lat = address.getLatitude();
//                double lng = address.getLongitude();
//                c = address.getLocality();
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return c;
//
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }



}
