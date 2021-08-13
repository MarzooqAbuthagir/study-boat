package com.we_connect_students;

class historyxpensemodel {
    String Qno;
    String Question;
    String wrong;
    String Explain;
    String Answer;
    String option1;
    String option2;
    String option3;
    String option4;

    public historyxpensemodel(String Qno, String Question, String wrong, String Explain, String Answer, String option1, String option2, String option3, String option4) {
        this.Qno = Qno;
        this.Question = Question;
        this.wrong = wrong;
        this.Explain = Explain;
        this.Answer = Answer;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;

        this.option4=option4;
    }

    public String getQno() {
        return this.Qno;
    }
    public String getQuestion() {
        return this.Question;
    }

    public String getwrong() {
        return this.wrong;
    }

    public String getExplain() {
        return this.Explain;
    }
    public String getAnswer() {
        return this.Answer;
    }

    public String getoption1() {
        return this.option1;
    }
    public String getoption2() {
        return this.option2;
    }
    public String getoption3() {
        return this.option3;
    }
    public String getoption4() {
        return this.option4;
    }

}
