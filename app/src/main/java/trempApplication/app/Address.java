package trempApplication.app;

import java.util.UUID;

public class Address {
    UUID id;
    String streetAddress;
    String city;
    String buildingNumber;
    String postalCode;
    double latitude;
    double longitude;

    public Address(UUID id, String streetAddress, String city, String buildingNumber, String postalCode, double latitude, double longitude) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.city = city;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
