package org.gigtool.gigtool.storage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Robin Harris
 * This class describes the type of equipment. For example drum hardware.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TypeOfEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;


    public TypeOfEquipment(String name, String description){
        this.name = name;
        this.description = description;
    }
}
