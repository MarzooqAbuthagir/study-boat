package com.we_connect_students;

public class videofive {

    String student;
    String studentComments;
    String AdminComments;
    String studentAttach;
    String AdminAttach;


    public videofive(String student, String studentComments, String adminComments, String studentAttach, String adminAttach) {
        this.student = student;
        this.studentComments = studentComments;
        this.AdminComments = adminComments;
        this.studentAttach = studentAttach;
        this.AdminAttach = adminAttach;

    }

    public String getStudent() {
        return student;
    }

    public String getStudentComments() {
        return studentComments;
    }

    public String getAdminComments() {
        return AdminComments;
    }


    public String getStudentAttach() {
        return studentAttach;
    }

    public String getAdminAttach() {
        return AdminAttach;
    }


}
