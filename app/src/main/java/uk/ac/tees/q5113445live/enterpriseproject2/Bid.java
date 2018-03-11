package uk.ac.tees.q5113445live.enterpriseproject2;

/**
 * Created by Luke on 11/03/2018.
 */

public class Bid
{
    String driverName;
    String price;

    Bid()
    {
        driverName = "";
        price ="";
    }

    Bid(String dN, String p)
    {
        driverName = dN;
        price = p;

    }

    public String getDriverName() {return driverName;}
    public void setDriverName(String driverName) {this.driverName = driverName;}
    public String getPrice() {return price;}
    public void setPrice(String price) {this.price = price;}
}
