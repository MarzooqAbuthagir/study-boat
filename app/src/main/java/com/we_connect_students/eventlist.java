package com.we_connect_students;

public class eventlist {
    String testPackageId;
    String testName;
    String noOfQuestions;
    String duration;
    String flag;

    public eventlist(String testPackageId, String testName, String noOfQuestions, String duration, String flag) {
        this.testPackageId = testPackageId;
        this.testName = testName;
        this.noOfQuestions = noOfQuestions;
        this.duration = duration;
        this.flag = flag;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTestPackageId() {
        return testPackageId;
    }

    public String getTestName() {
        return testName;
    }

    public String getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setTestPackageId(String testPackageId) {
        this.testPackageId = testPackageId;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setNoOfQuestions(String noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
