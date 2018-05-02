package uk.ac.tees.q5113445live.enterpriseproject2;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

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

    User()

    {
        name = "";
        email = "";
        houseNumber = "";
        postcode = "";
        cityName = "";
        number = "";
        regNumber = "";
        wallet = "";
    }

    User(String wall)
    {
        wallet = wall;

    }

    User(String n, String e,String num,String hn, String pc, String c, String wal)
    {
        name= n;
        email = e;
        number = num;
        regNumber = "";
        houseNumber = hn;
        postcode = pc;
        cityName = c;
        driver = false;
        wallet = wal;
    }

    User(String n, String e, Location l, String num, String reg, String wal)
    {
        name= n;
        email = e;
        number = num;
        regNumber = reg;
        driver = true;
        wallet = wal;
    }

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
}
