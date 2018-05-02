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
//    Location()
//    {
//        houseNum = "";
//        postCode = "";
//        context = null;
//    }
    Location(String hNum, String pCode,String c, Context con)
    {
        houseNum = hNum;
        postCode = pCode;
        context = con;
        city = c;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    public String getCity(String postcode)
    {
        String c = null;
        Geocoder gps = new Geocoder(context, Locale.getDefault());
        if (gps.isPresent()) {
            try {
                List<Address> list = gps.getFromLocationName("NE8 2TL", 10);
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

    public void setCity(String city)
    {
        this.city = city;
    }

    public Location getLocation()
    {
        return this;
    }
}
