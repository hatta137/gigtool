package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
@Transactional
public class TestUtils {

    @Autowired
    private AddressService addressService;
    @Autowired
    private TypeOfLocationService typeOfLocationService;
    @Autowired
    private TypeOfEquipmentService typeOfEquipmentService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private RoleInTheBandService roleInTheBandService;
    @Autowired
    private BandService bandService;
    @Autowired
    private TypeOfGigService typeOfGigService;
    @Autowired
    private GigService gigService;
    @Autowired
    private EquipmentService equipmentService;

    public BandCreate getRandomBandCreate() {
        return new BandCreate(
                UUID.randomUUID() + "name",
                getRandomGenreResponse().getBody().getId(),
                getRandomRoleInTheBandResponse().getBody().getId()
        );
    }

    public ResponseEntity<BandResponse> getRandomBandResponse() {
        return bandService.addBand( getRandomBandCreate() );
    }

    public GenreCreate getRandomGenreCreate() {
        return new GenreCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public ResponseEntity<GenreResponse> getRandomGenreResponse() {

        return genreService.addGenre( getRandomGenreCreate() );
    }

    public TypeOfGigCreate getRandomTypeOfGigCreate() {
        return new TypeOfGigCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public ResponseEntity<TypeOfGigResponse> getRandomTypeOfGigResponse() {

        return typeOfGigService.addTypeOfGig( getRandomTypeOfGigCreate() );
    }

    public RoleInTheBandCreate getRandomRoleInTheBandCreate() {
        return new RoleInTheBandCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public ResponseEntity<RoleInTheBandResponse> getRandomRoleInTheBandResponse() {

        return roleInTheBandService.addRoleInTheBand( getRandomRoleInTheBandCreate() );
    }

    public TypeOfEquipmentCreate getRandomTypeOfEquipmentCreate() {
        return new TypeOfEquipmentCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public ResponseEntity<TypeOfEquipmentResponse> getRandomTypeOfEquipmentResponse() {

        return typeOfEquipmentService.addTypeOfEquipment( getRandomTypeOfEquipmentCreate() );
    }

    public AddressCreate getRandomAddressCreate() {
        Random random = new Random();
        return new AddressCreate(
                random.nextInt(100),
                UUID.randomUUID() + "street",
                UUID.randomUUID() + "zipcode",
                UUID.randomUUID() + "country",
                UUID.randomUUID() + "city"
        );
    }

    public ResponseEntity<AddressResponse> getRandomAddressResponse() {

        AddressCreate addressToSave = getRandomAddressCreate();

        return addressService.addNewAddress(addressToSave);
    }

    public TypeOfLocationCreate getRandomTypeOfLocationCreate() {
        return new TypeOfLocationCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description"
        );
    }

    public ResponseEntity<TypeOfLocationResponse> getRandomTypeOfLocationResponse() {

        TypeOfLocationCreate typeOfLocationToSave = getRandomTypeOfLocationCreate();

        return typeOfLocationService.addTypeOfLocation( typeOfLocationToSave );
    }

    public LocationCreate getRandomLocationCreate() {
        return new LocationCreate(
                getRandomAddressResponse().getBody().getId(),
                getRandomTypeOfLocationResponse().getBody().getId()
        );
    }

    public ResponseEntity<LocationResponse> getRandomLocationResponse() {

        return locationService.addLocation( getRandomLocationCreate() );
    }

    public EquipmentCreate getRandomEquipmentCreate() {
        Random random = new Random();

        return new EquipmentCreate(
                UUID.randomUUID() + "name",
                UUID.randomUUID() + "description",
                getRandomTypeOfEquipmentResponse().getBody().getId(),
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(100),
                LocalDate.now(),
                getRandomLocationResponse().getBody().getId(),
                random.nextFloat(100)
        );
    }

    public ResponseEntity<EquipmentResponse> getRandomEquipmentResponse() {

        return equipmentService.addEquipment( getRandomEquipmentCreate() );
    }

    public GigCreate getRandomGigCreate() {

        GigCreate gigCreate = new GigCreate();
        gigCreate.setAddress( getRandomAddressResponse().getBody().getId() );
        gigCreate.setName( UUID.randomUUID() + "name" );
        gigCreate.setStartTime(LocalDateTime.now());
        gigCreate.setEndTime(LocalDateTime.now());
        gigCreate.setDescription( UUID.randomUUID() + "description" );
        gigCreate.setTypeOfGig( getRandomTypeOfGigResponse().getBody().getId() );
        gigCreate.setBand( getRandomBandResponse().getBody().getId());

        return  gigCreate;
    }

    public ResponseEntity<GigResponse> getRandomGigResponse() {
        return gigService.addGig( getRandomGigCreate() );
    }

    public RentalCreate getRandomRentalCreate() {

        RentalCreate rentalCreate = new RentalCreate();
        rentalCreate.setAddress( getRandomAddressResponse().getBody().getId() );
        rentalCreate.setName( UUID.randomUUID() + "name" );
        rentalCreate.setStartTime(LocalDateTime.now());
        rentalCreate.setEndTime(LocalDateTime.now());
        rentalCreate.setDescription( UUID.randomUUID() + "description" );
        rentalCreate.setResponsiblePerson( UUID.randomUUID() + "responsible person" );

        return rentalCreate;
    }
}
