package com.solvd.custom.enums;

public enum DayOfWeek {
    MONDAY("Monday", true),
    TUESDAY("Tuesday", true),
    WEDNESDAY("Wednesday", true),
    THURSDAY("Thursday", true),
    FRIDAY("Friday", true),
    SATURDAY("Saturday", false),
    SUNDAY("Sunday", false);

    private final String name;
    private final boolean isWeekday;

    DayOfWeek(String name, boolean isWeekday) {
        this.name = name;
        this.isWeekday = isWeekday;
    }

    public String getName() {
        return name;
    }

    public boolean isWeekday() {
        return isWeekday;
    }

    public void printDetails() {
        System.out.println("Name: " + name + ", Is Weekday: " + isWeekday);
    }
}
