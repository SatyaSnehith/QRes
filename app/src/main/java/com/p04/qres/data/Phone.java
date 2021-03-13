package com.p04.qres.data;

public class Phone extends Text{
    String number;

    public Phone(String value) {
        super(value);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
