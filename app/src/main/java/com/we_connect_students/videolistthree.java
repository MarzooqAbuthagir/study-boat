package com.we_connect_students;

public class videolistthree {

    String chapterid;
    String chapterName;
    String count;

    public videolistthree(String chapterid, String chapterName, String count) {
        this.chapterid = chapterid;
        this.chapterName = chapterName;
        this.count = count;
    }

    public String getChapterid() {
        return chapterid;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getCount() {
        return count;
    }

    public void setChapterid(String chapterid) {
        this.chapterid = chapterid;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
