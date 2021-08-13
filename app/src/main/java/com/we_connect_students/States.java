package com.we_connect_students;

class States {

    String  Name;
    String State;
    public States(String stateName, String stateId) {
        this.Name=stateName;
        this.State=stateId;
    }


    public String GETNAME() {
        return Name;
    }

    public String GETID() {
        return State;
    }

}
