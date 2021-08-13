package com.we_connect_students;

class Subcriptionlist {
String serialNo;
    String testPackageIndexId;
    String testName;
    String rightAnswerCount;
    String percentage;
    String totalquestion;


    public Subcriptionlist(String serialNo, String testPackageIndexId, String testName, String rightAnswerCount, String percentage,String totalquestion) {


        this.serialNo=serialNo;
        this.testPackageIndexId=testPackageIndexId;
        this.testName=testName;
        this.rightAnswerCount=rightAnswerCount;
        this.percentage=percentage;
        this.totalquestion=totalquestion;
    }




    public String getserialNo() {
        return this.serialNo;
    }
    public String gettestPackageIndexId() {
        return this.testPackageIndexId;
    }
    public String gettestName() {
        return this.testName;
    }
    public String getrightAnswerCount() {
        return this.rightAnswerCount;
    }
    public String getpercentage() {
        return this.percentage;
    }

    public String getTotalquestion() {
        return totalquestion;
    }
}
