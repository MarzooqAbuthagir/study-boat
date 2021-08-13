package com.we_connect_students;

public class Detailrepot {
    String serialNumber;
    String date;
    String rightAnswerCount;
    String percentage;
    String duration;
    String time;
    String total;

Detailrepot() {}

    public Detailrepot(String serialNumber, String date, String rightAnswerCount, String percentage, String duration,String time,String total) {

       this. serialNumber=serialNumber;
        this. date=date;
        this. rightAnswerCount=rightAnswerCount;
        this. percentage=percentage;
        this. duration=duration;
        this.time=time;
        this.total=total;


    }
    public String getserialNumber() {
        return this.serialNumber;
    }
    public String getdate() {
        return this.date;
    }

    public String getrightAnswerCount() {
        return this.rightAnswerCount;
    }

    public String getpercentage() {
        return this.percentage;
    }

    public String getduration() {
        return this.duration;
    }

    public String getTime() {
        return time;
    }

    public String getTotal() {
        return total;
    }

}
