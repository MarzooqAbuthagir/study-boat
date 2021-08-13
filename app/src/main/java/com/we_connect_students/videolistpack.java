package com.we_connect_students;

public class videolistpack {
    String PackageIndexId;
    String PackageName;
    String Price;
    String NoofVideos;


    String examName;
    String subTitle;
    String subjectCount;
    String flag;

    public videolistpack(){

    }

    public videolistpack(String packageIndexId, String packageName, String price, String noofVideos, String examName, String subTitle, String subjectCount, String flag) {
        PackageIndexId = packageIndexId;
        PackageName = packageName;
        Price = price;
        NoofVideos = noofVideos;
        this.examName = examName;
        this.subTitle = subTitle;
        this.subjectCount = subjectCount;
        this.flag = flag;
    }

    public String getPackageIndexId() {
        return PackageIndexId;
    }

    public String getPackageName() {
        return PackageName;
    }

    public String getPrice() {
        return Price;
    }

    public String getNoofVideos() {
        return NoofVideos;
    }

    public String getExamName() {
        return examName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getSubjectCount() {
        return subjectCount;
    }

    public String getFlag() {
        return flag;
    }

    public void setPackageIndexId(String packageIndexId) {
        PackageIndexId = packageIndexId;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setNoofVideos(String noofVideos) {
        NoofVideos = noofVideos;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setSubjectCount(String subjectCount) {
        this.subjectCount = subjectCount;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


}
