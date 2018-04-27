package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Luke on 27/04/2018.
 */

public class Location
{
    private String houseNum;
    private String postCode;
    private String city;
    private Context context;
    Location(String hNum, String pCode, Context con)
    {
        houseNum = hNum;
        postCode = pCode;
        context = con;
    }

    public String getLocation()
    {
        String c = null;
        Geocoder gps = new Geocoder(context, Locale.getDefault());
        if (gps.isPresent()) {
            try {
                List<Address> list = gps.getFromLocationName("NE8 2TL", 100);
                Address address = list.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();
                c = address.getLocality();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return c;
    }
}
