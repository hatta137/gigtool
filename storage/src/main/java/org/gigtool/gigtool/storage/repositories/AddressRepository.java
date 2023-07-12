package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
