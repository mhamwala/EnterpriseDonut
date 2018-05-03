package uk.ac.tees.q5113445live.enterpriseproject2;

import com.google.android.gms.location.places.Place;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class describing an Advert. These are the objects that users will put on the app,
 * to be collected and delivered. Drivers will bid on these objects also.
 */

public class Advert implements Serializable
{
    //Initialise variables
    String name;
    String deliveryType;
    Location from;
    Location to;
    String weight;
    String size;
    Location location;
    HashMap<String, HashMap<String, String>> bid;

    //Blank Advert
    Advert()
    {
        name = "";
        deliveryType= "";
        from = null;
        to = null;
        weight = "";
        size="";
        bid = null;
    }

    //Constructor for full advert.
    Advert(String n, String dt, Place c, Place d, String w, String s, HashMap b)
    {
        name = n;
        deliveryType= dt;
        from = new Location(c);
        to = new Location(d);
        weight= w;
        size = s;
        bid = b;
    }
    Advert(String n, String dt, Place c, Place d, String w, String s)
    {
        name = n;
        deliveryType= dt;
        from = new Location(c);
        to = new Location(d);
        weight= w;
        size = s;
        bid = null;
    }
    Advert(Advert a)
    {
        name = a.getName();
        deliveryType= a.getDeliveryType();
        from = a.getFrom();
        to = a.getTo();
        weight = a.getWeight();
        size= a.getSize();
    }

    //Required getters and setters
    public String getDeliveryType() {
        return deliveryType;
    }
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public HashMap<String, HashMap<String, String>> getBid() {
        return bid;
    }

    public void setBid(HashMap<String, HashMap<String, String>> bid)
    {
        this.bid = bid;
    }
}
