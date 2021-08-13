package com.we_connect_students;

public class packlistnew {
    String PackageIndexId;
    String PackageName;
    String Price;
    String NoofQuestions;


    String examName;
    String subTitle;
    String subjectCout;
    String flag;

    public packlistnew(){

    }



    public packlistnew(String packageIndexId, String packageName, String price, String noofQuestions, String examName, String subTitle, String subjectCout, String flag) {

        this.PackageIndexId=packageIndexId;
        this.PackageName=packageName;
        this.Price=price;
        this.NoofQuestions=noofQuestions;

        this.examName=examName;
        this.subTitle=subTitle;
        this.subjectCout=subjectCout;
        this.flag=flag;


    }


    public String getpackageIndexId() {
        return this.PackageIndexId;
    }
    public String getpackageName() {
        return this.PackageName;
    }

    public String getprice() {
        return this.Price;
    }

    public String getnoofQuestions() {
        return this.NoofQuestions;
    }


    public String getexamName() {
        return this.examName;
    }
    public String getsubTitle() {
        return this.subTitle;
    }

    public String getsubjectCout() {
        return this.subjectCout;
    }

    public String getflag() {
        return this.flag;
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

    public void setNoofQuestions(String noofQuestions) {
        NoofQuestions = noofQuestions;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setSubjectCout(String subjectCout) {
        this.subjectCout = subjectCout;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
