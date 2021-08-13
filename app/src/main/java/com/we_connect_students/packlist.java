package com.we_connect_students;

class packlist {
    String Sname;
    String Tid;
    String noquestion;
    String tname;
    String examTitle;
    String subTitle;
    String testTime;
    String checktest;
    public packlist(String subjectName, String testPackageIndexId, String noOfQuestion, String testName, String examTitle, String subTitle, String testTime, String retestno) {
        this.Sname=subjectName;
        this.Tid=testPackageIndexId;
        this.noquestion=noOfQuestion;
        this.tname=testName;
        this.examTitle=examTitle;
        this.subTitle=subTitle;

        this.testTime=testTime;
        this.checktest=retestno;
    }

    public String getsname() {
        return this.Sname;
    }
    public String gettid() {
        return this.Tid;
    }

    public String getnoq() {
        return this.noquestion;
    }

    public String gettname() {
        return this.tname;
    }


    public String getexamTitle() {
        return this.examTitle;
    }

    public String getsubTitle() {
        return this.subTitle;
    }

    public String gettestTime() {
        return this.testTime;
    }
    public String getchecktest() {
        return this.checktest;
    }
}
