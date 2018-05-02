package uk.ac.tees.q5113445live.enterpriseproject2;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

/**
 * Created by Luke on 13/02/2018.
 */

public class User
{
    String name;
    String cityName;
    String postcode;
    String houseNumber;
    String email;
    String regNumber;
    String wallet;
    boolean driver = false;
    String number;
<<<<<<< HEAD
    float baseRating;
    float newRating;
=======
    String baseRating;
    int newRating;
    Location location = null;
>>>>>>> cda756e79f58cf063c7c56c5acfcb24b3fdfb80a

    User()
    {
        name = "";
        email = "";
        houseNumber = "";
        location = null;
        regNumber = "";
        wallet = "";

    }

    User(String wall)
    {
        wallet = wall;

    }
//
//    User(String n, String e,String num,String hn, String pc, String c, String wal)
//    {
//        name= n;
//        email = e;
//        number = num;
//        regNumber = "";
//        houseNumber = hn;

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    //        postcode = pc;
//        cityName = c;
//        driver = false;
//        wallet = wal;
//    }
    User(String n, String e, String num, Place p, String wal)
    {
        name= n;
        email = e;
        number = num;
        regNumber = "";
        location = new Location((String) p.getAddress(),Double.toString(p.getLatLng().latitude),Double.toString(p.getLatLng().longitude));
        driver = false;
        wallet = wal;
    }

//    User(String n, String e, Location l, String num, String reg, String wal)
//    {
//        name= n;
//        email = e;
//        number = num;
//        regNumber = reg;
//        driver = true;
//        wallet = wal;
//    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getRegNumber()
    {
        return regNumber;
    }


    public void setRegNumber(String regNumber)
    {
        this.regNumber = regNumber;
    }

    public boolean isDriver()
    {
        return driver;
    }

    public void setDriver(boolean driver)
    {
        this.driver = driver;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCityName()
    {
        return cityName;
    }

    public String getHouseNumber()
    {
        return houseNumber;
    }

    public String getPostcode()
    {
        return postcode;
    }
    public Location getLocation() {
        return location;
    }

    public float getRating() {return baseRating;}

    public void setRating(float rating){
        if(baseRating == 5 && rating > baseRating)
        {
            baseRating = 5;
        }
        else if(baseRating == 0 && rating < baseRating)
        {
            baseRating = 0;
        }
        else
        {
            if(rating > baseRating)
            {
                baseRating += 0.5;
            }
            else
            {
                baseRating -= 0.5;
            }
        }
        this.baseRating = baseRating;
    }
}
