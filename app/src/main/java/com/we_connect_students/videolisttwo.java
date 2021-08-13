package com.we_connect_students;

public class videolisttwo {

    String unitid;
    String unitName;
    String count;


    public videolisttwo(String unitid, String unitName, String count) {
        this.unitid = unitid;
        this.unitName = unitName;
        this.count = count;
    }

    public String getUnitid() {
        return unitid;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getCount() {
        return count;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
