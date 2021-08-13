package com.we_connect_students;

public class ChildTerbarus {

    String chapterName;
    String overallCount;
    String correctCount;
    String percentage;

    public ChildTerbarus(String chapterName, String overallCount, String correctCount, String percentage) {

        this.chapterName=chapterName;
        this.overallCount=overallCount;
        this.correctCount=correctCount;
        this.percentage=percentage;
    }


    public String getchapterName() {
        return this.chapterName;
    }
    public String getoverallCount() {
        return this.overallCount;
    }

    public String getcorrectCount() {
        return this.correctCount;
    }


    public String getpercentage() {
        return this.percentage;
    }


}
