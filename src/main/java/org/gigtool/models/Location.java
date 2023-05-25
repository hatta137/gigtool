package org.gigtool.models;


/***
 * Author: Robin Harris
 * The Location class represents a physical location and contains information about its address and type.
 */
public class Location {

    private Address address;
    private TypeOfLocation typeOfLocation;

    /***
     * Constructor with all attributes.
     * @param address
     * @param typeOfLocation
     */
    public Location(Address address, TypeOfLocation typeOfLocation){
        this.address = address;
        this.typeOfLocation = typeOfLocation;
    }

    /***
     * Getter & Setter
     * Author: Robin Harris
     */
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public TypeOfLocation getTypeOfLocation() {
        return typeOfLocation;
    }
    public void setTypeOfLocation(TypeOfLocation typeOfLocation) {
        this.typeOfLocation = typeOfLocation;
    }
}

