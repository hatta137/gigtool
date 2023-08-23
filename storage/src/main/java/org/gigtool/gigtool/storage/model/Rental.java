package org.gigtool.gigtool.storage.model;



import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * @author Dario Daßler
 * This class represents a loan of equipment.
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Rental extends Happening {
    private String responsiblePerson;
}
