package org.gigtool.gigtool.storage.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.services.EquipmentList;

import java.util.*;

/***
 * Author: Hendrik Lendeckel
 * This class represents a kind of preset for equipment.
 * The musician can create bands and give them predefined equipment lists.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    private Genre genre;
    @ManyToMany
    private List<RoleInTheBand> listOfRole;
    @ManyToMany
    private List<Equipment> equipmentList;


}
