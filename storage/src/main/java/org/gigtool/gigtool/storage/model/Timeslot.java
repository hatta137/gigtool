package org.gigtool.gigtool.storage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/***
 * Author: Dario Daßler
 * The Timeslot class represents a period of time within a single day.
 * This class is used to store information about a specific time slot, including its start time, end time, and date.
* */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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
}
