package org.gigtool.models;

/***
 * Author: Max Schelenz
 * This class describes the role a musician can have in a band.
 */

public class RoleInTheBand {
    private String name;
    private String description;

    public RoleInTheBand(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
