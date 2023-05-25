package org.gigtool.models;

/***
 * Author: Max Schelenz
 * The TypeOfGig class represents a type of gig, with a name and a description.
 */

public class TypeOfGig {

    private String name;
    private String description;

    /**
     * Constructor with all attributes.
     * @param name Name of the Gigtype
     * @param description Description of the TYpe of Gig
     */
    public TypeOfGig(String name, String description) {
        this.name=name;
        this.description=description;
    }

    /***
     * Getter & Setter
     * Author Max Schelenz
     */

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
