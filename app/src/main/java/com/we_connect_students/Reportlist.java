package com.we_connect_students;

class Reportlist {
    String SubjectName;

    String NoOfQuestion;

    String TestName;

    String CorrectAnswer;
    String WrongAnswer;
    String lastIndexId;

    public Reportlist(String subjectName, String noOfQuestion, String testName, String correctAnswer, String wrongAnswer, String lastIndexId) {
        this.SubjectName=subjectName;
        this.NoOfQuestion=noOfQuestion;
        this.TestName=testName;
        this.CorrectAnswer=correctAnswer;
        this.WrongAnswer=wrongAnswer;
        this.lastIndexId=lastIndexId;
    }


    public String getsname() {
        return this.SubjectName;
    }

    public String getnques() {
        return this.NoOfQuestion;
    }

    public String gettname() {
        return this.TestName;
    }

    public String getcans() {
        return this.CorrectAnswer;
    }

    public String getwans() {
        return this.WrongAnswer;
    }

    public String getlastIndexId() {
        return this.lastIndexId;
    }
}
