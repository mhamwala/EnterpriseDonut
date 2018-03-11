package uk.ac.tees.q5113445live.enterpriseproject2;

import android.app.Activity;

/**
 * Created by arsen on 16/02/2018.
 */

public class Advert
{
    String name;
    String deliveryType;
    String from;
    String to;
    String weight;
    String size;


    /**
     * A map of sample (dummy) items, by ID.
     */

    Advert()
    {
        name = "";
        deliveryType= "";
        from = "";
        to ="";
        weight = "";
        size="";
    }

    Advert(String n, String dt, String c, String d, String w, String s)
    {
        name = n;
        deliveryType= dt;
        from = c;
        to = d;
        weight= w;
        size = s;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getFrom() {return from;    }

    public void setFrom(String from) {this.from = from; }

    public String getTo() {return to;}

    public void setTo(String to) {this.to = to;}

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


    // Calculating the Base/Minimum pay for a request to be sent out
    public void basePay(String from, String to, String size)
    {
        //Work out mileage from two places
        // mileage * size /5 * 2
    }
    public void findDistance(String from, String to, Activity a)
    {

    }






}
