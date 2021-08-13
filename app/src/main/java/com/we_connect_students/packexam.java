package com.we_connect_students;

public class packexam {

    String examIndexId;
    String examTittle;
    String subTittle;
    String logo;
    String startColor="";
    String  endColor="";

    public packexam() {
        this.examIndexId= examIndexId;
        this.examTittle= examTittle;
        this.subTittle= subTittle;
        this.logo = logo;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public String getExamIndexId() {
        return examIndexId;
    }

    public String getExamTittle() {
        return examTittle;
    }

    public String getSubTittle() {
        return subTittle;
    }

    public String getLogo() {
        return logo;
    }


    public void setExamIndexId(String examIndexId) {
        this.examIndexId = examIndexId;
    }

    public void setExamTittle(String examTittle) {
        this.examTittle = examTittle;
    }

    public void setSubTittle(String subTittle) {
        this.subTittle = subTittle;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStartColor() {
        return startColor;
    }

    public void setStartColor(String startColor) {
        this.startColor = startColor;
    }

    public String getEndColor() {
        return endColor;
    }

    public void setEndColor(String endColor) {
        this.endColor = endColor;
    }
}
