package uk.ac.tees.q5113445live.enterpriseproject2;

/**
 * Created by Luke on 13/02/2018.
 */

public class User
{
    String name;
    String location;
    String email;

    double number;

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    User()
    {
        name = "";
        email = "";

        location ="";

        number = 0;
    }
    User(String n, String e, String l,  int num)
    {
        name= n;
        email =e;
        location = l;

        number = num;
    }
    User(String n, String e, String l)
    {
        name= n;
        email =e;
        location = l;
        number = 0;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
