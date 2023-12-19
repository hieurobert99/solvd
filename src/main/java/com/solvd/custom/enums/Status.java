package com.solvd.custom.enums;

public enum Status {
    HEALTHY("Healthy", true),
    SICK("Sick", false);

    private final String description;
    private final boolean isHealthy;

    Status(String description, boolean isHealthy) {
        this.description = description;
        this.isHealthy = isHealthy;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHealthy() {
        return isHealthy;
    }

    public void printDetails() {
        System.out.println("Description: " + description + ", Is Healthy: " + isHealthy);
    }
}