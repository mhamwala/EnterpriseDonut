package uk.ac.tees.q5113445live.enterpriseproject2;

/**
 * Created by Luke on 13/02/2018.
 */

public class User
{
    String name;
    String location;
    String email;
    String uid;

    User()
    {
        name = "";
        email = "";
        location ="";
        uid = "";
    }
    User(String n, String e, String l, String id)
    {
        name= n;
        email =e;
        location = l;
        uid = id;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    User(String n, String e, String l)
    {
        name= n;
        email =e;
        location = l;

        uid = "";
    }
}
