package com.we_connect_students;

public class videolist {

    String subjectid;
    String subjectName;
    String count;

    public videolist(String subjectid, String subjectName, String count) {
        this.subjectid = subjectid;
        this.subjectName = subjectName;
        this.count = count;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getCount() {
        return count;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
