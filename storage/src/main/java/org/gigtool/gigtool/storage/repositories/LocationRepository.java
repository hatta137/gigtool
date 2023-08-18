package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    List<Location> findByTypeOfLocationId(UUID typeOfEquipmentId );

    List<Location> findByAddressId( UUID addressId );
}
