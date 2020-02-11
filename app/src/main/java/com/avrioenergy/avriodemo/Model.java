package com.avrioenergy.avriodemo;

public class Model {
    String name;
    int statusID;

    public void setName(String name) {
        this.name = name;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getName() {
        return name;
    }

    public int getStatusID() {
        return statusID;
    }
    public Model()
    {

    }
    public Model(String name, int statusID) {
        this.name = name;
        this.statusID = statusID;
    }
}
