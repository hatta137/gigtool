package org.gigtool.models;

/**
 * Author: Robin Harris
 * This class describes the type of equipment. For example drum hardware.
 */

public class TypeOfEquipment {

    private String name;
    private String description;

    public TypeOfEquipment(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
