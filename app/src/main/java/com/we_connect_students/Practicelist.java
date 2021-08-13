package com.we_connect_students;

public  class Practicelist {
    String subjectName;
    String count;
    String Type;

    public Practicelist(String subjectName, String count) {
        this.subjectName=subjectName;
        this.count=count;

    }

    public String getsubjectName() {
        return this.subjectName;
    }
    public String getcount() {
        return this.count;
    }
}
