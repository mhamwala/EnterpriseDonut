package uk.ac.tees.q5113445live.enterpriseproject2;

/**
 * Created by Luke on 13/02/2018.
 */

public class User
{
    String name;
    String location;
    String email;
    String regNumber;
    boolean driver = false;

    String number;




    User()
    {
        name = "";
        email = "";
        location ="";
        number = "";
        regNumber = "";
    }
    User(String n, String e, String l,  String num)
    {
        name= n;
        email = e;
        location = l;
        number = num;
        regNumber = "";
        driver = false;
    }
    User(String n, String e, String l,  String num, String reg)
    {
        name= n;
        email = e;
        location = l;
        number = num;
        regNumber = reg;
        driver = true;
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
