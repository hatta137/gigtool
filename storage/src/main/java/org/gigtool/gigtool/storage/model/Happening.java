package org.gigtool.gigtool.storage.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/***
 * @author Dario Daßler
 * This class is the parent class of Gig and Rental and contains details about the name, description, equipment list, address and timeslot.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
@Setter
public abstract class Happening {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    private Address address;
    @ManyToMany
    private List<Equipment> equipmentList;

}
