package com.we_connect_students;

public class Citys {

String City="";
String Cid="";
    public Citys(String cityName, String cityId) {
        this.City=cityName;
        this.Cid=cityId;
    }


    public String GETCITY() {
        return City;
    }
    public String GETCID() {
        return Cid;
    }
}
