package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.RoleInTheBand;
import org.gigtool.gigtool.storage.repositories.RoleInTheBandRepository;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandCreate;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleInTheBandService {

    private final RoleInTheBandRepository roleInTheBandRepository;

    public RoleInTheBandService (RoleInTheBandRepository roleInTheBandRepository) {
        this.roleInTheBandRepository = roleInTheBandRepository;
    }

    public ResponseEntity<RoleInTheBandResponse> addRoleInTheBand (RoleInTheBandCreate roleInTheBandCreate) {

        if ((roleInTheBandCreate.getName() == null) && (roleInTheBandCreate.getDescription() == null)) {
            return ResponseEntity.badRequest().build();
        }

        RoleInTheBand newRoleInTheBand = new RoleInTheBand();

        newRoleInTheBand.setName(roleInTheBandCreate.getName());
        newRoleInTheBand.setDescription(roleInTheBandCreate.getDescription());

        RoleInTheBand savedRoleInTheBand = roleInTheBandRepository.saveAndFlush(newRoleInTheBand);

        return ResponseEntity.ok(new RoleInTheBandResponse(savedRoleInTheBand));
    }



}
