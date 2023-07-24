package org.gigtool.gigtool.storage.repositories;

import org.gigtool.gigtool.storage.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Override
    Optional<Address> findById(UUID uuid);
}
