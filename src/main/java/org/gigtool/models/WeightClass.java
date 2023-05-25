package org.gigtool.models;


/***
 * Author: Max Schelenz
 * This class describes a weight class in which an instrument is based on its own weight.
 */

public class WeightClass {

    private String name;
    private String description;
    private int weightStart;
    private int duration;

    /**
     * Constructor with all attributes.
     * @param name
     * @param description
     * @param weightStart
     * @param duration
     */
    public WeightClass(String name, String description, int weightStart, int duration) {
        this.name=name;
        this.description=description;
        this.weightStart=weightStart;
        this.duration=duration;
    }

    /***
     * Getter & Setter
     * Author Max Schelenz
     */

    public void setName (String name) {
        this.name=name;
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
    public void setWeightStart(int weightStart) {
        this.weightStart = weightStart;
    }
    public int getWeightStart() {
        return weightStart;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }

}







