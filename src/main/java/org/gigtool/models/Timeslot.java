package org.gigtool.models;

import java.time.LocalDate;
import java.time.LocalTime;

/***
 * Author: Dario Daßler
 * The Timeslot class represents a period of time within a single day.
 * This class is used to store information about a specific time slot, including its start time, end time, and date.
* */

public class Timeslot {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;

    /**
     * Author: Dario Daßler
     * Constructs a new Timeslot object with the specified start time, end time, and date.
     *
     * @param startTime The start time of the time slot, represented as a LocalTime object.
     * @param endTime The end time of the time slot, represented as a LocalTime object.
     * @param date The date of the time slot, represented as a LocalDate object.
     */
    public Timeslot(LocalTime startTime, LocalTime endTime, LocalDate date) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    /***
     * Getter & Setter
     * Author: Dario Daßler
     */
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
