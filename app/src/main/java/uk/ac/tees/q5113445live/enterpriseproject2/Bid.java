package uk.ac.tees.q5113445live.enterpriseproject2;

import java.util.ArrayList;

/**
 * Created by Luke on 11/03/2018.
 */

public class Bid
{
    String driverName;
    String price;
    ArrayList<String> testBid;

    //Blank bid
    Bid(String s)
    {
        driverName = "";
        price ="";
    }

    //Constructor for list only
    Bid(ArrayList tb)
    {
        testBid = tb;
    }

    //Constructor for full bid.
    Bid(String dN, String p, ArrayList tb)
    {
        driverName = dN;
        price = p;
        testBid = tb;

    }

    public String getDriverName() {return driverName;}
    public void setDriverName(String driverName) {this.driverName = driverName;}
    public String getPrice() {return price;}
    public void setPrice(String price)
    {
        this.price = price;
    }

    public ArrayList<String> getListBid() {
        return testBid;
    }
    public void setBid(ArrayList<String>bid)
    {
        this.testBid = bid;
    }
}
