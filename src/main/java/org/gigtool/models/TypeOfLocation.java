package org.gigtool.models;

/***
 * Author: Max Schelenz
 * The class TypeOfLocation represents a type of location, such as "concert hall" or "outdoor festival site".
 */

public class TypeOfLocation {

    private String name;
    private String description;

    /**
     * Constructor with all attributes.
     * @param name
     * @param description
     */
    public TypeOfLocation(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /***
     * Getter & Setter
     * Author Max Schelenz
     */
    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

}
