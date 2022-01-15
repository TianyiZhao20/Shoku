package com.example.shoku;

public class krogerLocation {
    private String locationId;
    private String locationName;

    public krogerLocation(String locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return locationName;
    }
}
