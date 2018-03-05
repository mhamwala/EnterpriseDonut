package uk.ac.tees.q5113445live.enterpriseproject2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.tees.q5113445live.enterpriseproject2.dummy.DummyContent;

/**
 * Created by arsen on 16/02/2018.
 */

public class Delivery
{
   String deliveryType;
   String collect;String deliver;
   String pay;
  String weight;
    String size;
    public static final List<DummyContent.DummyItem> ITEMS = new ArrayList<DummyContent.DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyContent.DummyItem> ITEM_MAP = new HashMap<String, DummyContent.DummyItem>();
    Delivery()
    {
        deliveryType= "";
        collect = "";
        deliver ="";
        pay = "";
        weight = "";
        size="";
    }

    Delivery(String dt, String c, String d, String p, String w, String s)
    {
       deliveryType= dt;
        collect = c;
        deliver = d;
        pay = p;
        weight= w;
        size = s;


    }
    private static void addItem(DummyContent.DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getCollect() {return collect;    }

    public void setCollect(String collect) {this.collect = collect;    }

    public String getDeliver() {return deliver;}

    public void setDeliver(String deliver) {this.deliver = deliver;}

    public String getDistance() {
        return collect;
    }

    public void setDistance(String distance) {
        this.collect = distance;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
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


    // Calculating the Base/Minimum pay for a request to be sent out
    public void basePay(String from, String to, String size)
    {
        //Work out mileage from two places
        // mileage * size /5 * 2
    }
    public void findDistance(String from, String to)
    {
        //This function will work out the mileage between two locations.
    }






}
