package com.solvd.custom.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void printLabel() {
        System.out.println("Label: " + label);
    }
}
