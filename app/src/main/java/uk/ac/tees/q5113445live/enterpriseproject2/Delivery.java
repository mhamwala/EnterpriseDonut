package uk.ac.tees.q5113445live.enterpriseproject2;

/**
 * Created by arsen on 16/02/2018.
 */

public class Delivery
{
    private String deliveryType;
    private String distance;
    private String pay;
    private String weight;
    private String size;

    Delivery()
    {

    }

    Delivery(String a, String b, String c, String d, String e)
    {
        this.deliveryType = a;
        this.distance = b;
        this.pay = c;
        this.weight = d;
        this.size = e;

    }
    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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





}
