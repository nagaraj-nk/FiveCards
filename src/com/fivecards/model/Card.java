package com.fivecards.model;

public class Card {

    private int value;
    private String type;
    private String displayNumber;
    private String code;

    public Card(int value, String type) {
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayNumber() {
        StringBuilder builder = new StringBuilder();
        
        
        
        builder.append(code).append(type);
        return builder.toString();
    }

    public void setDisplayNumber(String displayNumber) {
        this.displayNumber = displayNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Card() {
    }

}