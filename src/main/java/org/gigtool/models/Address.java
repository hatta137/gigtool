package org.gigtool.models;

/***
 * Author: Hendrik Lendeckel
 * This class represents an address. Addresses are used for locations, for example.
 */
public class Address {
    private String street;
    private String city;
    private String zipCode;
    private String country;
    private int houseNumber;

    public Address(String street, String city, String zipCode, String country, int houseNumber) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

}
