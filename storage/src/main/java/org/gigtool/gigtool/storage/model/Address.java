package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String street;
    private String city;
    private String zipCode;
    private String country;
    private int houseNumber;
    @OneToMany
    private List<Happening> happenings;

    public Address(String street, String city, String zipCode, String country, int houseNumber) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.houseNumber = houseNumber;
    }
}
