package com.solvd.hospital;

public abstract class Building {
    private final String name;
    private final String city;
    private final String address;

    public Building(String name, String city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
    }
    @Override
    public String toString() {
        return name + " in " + city + ", " + address;
    }
}
