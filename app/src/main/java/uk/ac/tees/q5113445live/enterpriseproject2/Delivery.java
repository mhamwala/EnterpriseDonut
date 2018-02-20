package uk.ac.tees.q5113445live.enterpriseproject2;

/**
 * Created by arsen on 16/02/2018.
 */

public class Delivery
{
    private String deliveryType;
    private String collect;
    private String deliver;
    private String pay;
    private String weight;
    private String size;

    //Need variables for delivered from, delivered to

    Delivery()
    {

    }

    Delivery(String a, String b, String c, String d, String e, String f)
    {
        this.deliveryType = a;
        this.collect = b;
        this.deliver =f;
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
