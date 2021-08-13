package com.we_connect_students;

class dashlistnew {
    String packageIndexId;
    String packageName;
    String price;
    String noofQuestions;
    String examName;
    String subTitle;
    String subjectCout;
    String flag;
    String expiryLeft;


    public dashlistnew(String packageIndexId, String packageName, String price, String noofQuestions, String examName, String subTitle, String subjectCout, String flag, String expiryLeft) {
        this.packageIndexId=packageIndexId;
        this.packageName=packageName;
        this.price=price;
        this.noofQuestions=noofQuestions;
        this.examName=examName;
        this.subTitle=subTitle;
        this.subjectCout=subjectCout;
        this.flag=flag;
        this.expiryLeft=expiryLeft;

    }

    public String getpackageIndexId() {
        return this.packageIndexId;
    }
    public String getpackageName() {
        return this.packageName;
    }


    public String getprice() {
        return this.price;
    }
    public String getnoofQuestions() {
        return this.noofQuestions;
    }

    public String getexamName() {
        return this.examName;
    }
    public String getsubTitle() {
        return this.packageName;
    }
    public String getsubjectCout() {
        return this.subjectCout;
    }
    public String getflag() {
        return this.flag;
    }

    public String getexpiryLeft() {
        return this.expiryLeft;
    }

}




