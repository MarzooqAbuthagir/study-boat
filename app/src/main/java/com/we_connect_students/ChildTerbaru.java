package com.we_connect_students;

class ChildTerbaru {
    String Chaptername;
    String Unitname;
   String isChecked="";
String isCheckeds;
String count;

    public ChildTerbaru(String chapterName, String unitname, String isChecked, String count) {
        this.Chaptername=chapterName;
        this.Unitname=unitname;
        this.isCheckeds=isChecked;
        this.count=count;


    }
    public String getchapter() {
        return this.Chaptername;
    }

    public String getunit() {
        return this.Unitname;
    }

    public String getisCheckeds() {
        return this.isCheckeds;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
    public String getcount() {
        return count;
    }


}
