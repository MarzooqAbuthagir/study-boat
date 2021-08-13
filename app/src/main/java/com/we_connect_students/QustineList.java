package com.we_connect_students;

class QustineList {
    String  Question;
    String Option1;
    String Option2;
    String Option3;
    String Option4;
    String Answer;
    String Qno;

    public QustineList(String question, String questionNumber, String option1, String option2, String option3, String option4, String answer) {
        this.Question=question;
        this.Qno=questionNumber;
        this.Option1=option1;
        this.Option2=option2;
        this.Option3=option3;
        this.Option4=option4;
        this.Answer=answer;

    }

    public String getquestion() {
        return this.Question;
    }

    public String getQno() {
        return this.Qno;
    }
    public String getoption1() {
        return this.Option1;
    }
    public String getoption2() {
        return this.Option2;
    }
    public String getoption3() {
        return this.Option3;
    }
    public String getoption4() {
        return this.Option4;
    }
    public String getanswer() {
        return this.Answer;
    }


}
