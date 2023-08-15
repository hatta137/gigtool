package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.RoleInTheBand;
import org.gigtool.gigtool.storage.repositories.RoleInTheBandRepository;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandCreate;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandRequest;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public ResponseEntity<List<RoleInTheBandResponse>> getAllRolesInTheBand() {

        List<RoleInTheBand> roleList = roleInTheBandRepository.findAll();

        List<RoleInTheBandResponse> responseList = roleList.stream()
                .map(RoleInTheBandResponse::new)
                .toList();

        return ResponseEntity.ok().body(responseList);
    }

    public ResponseEntity<RoleInTheBandResponse> getRoleInTheBandById(UUID roleId) {

        Optional<RoleInTheBand> roleOptional = roleInTheBandRepository.findById(roleId);

        return roleOptional.map(roleInTheBand -> ResponseEntity.ok(new RoleInTheBandResponse(roleInTheBand)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<RoleInTheBandResponse> updateRoleInTheBand(UUID roleId, RoleInTheBandRequest roleRequest) {

        if (roleId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<RoleInTheBand> existingRole = roleInTheBandRepository.findById(roleId);

        if (existingRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RoleInTheBand updatedRole = existingRole.get();

        if (roleRequest.getName() != null) {
            updatedRole.setName(roleRequest.getName());
        }

        if (roleRequest.getDescription() != null) {
            updatedRole.setDescription(roleRequest.getDescription());
        }

        RoleInTheBand savedRole = roleInTheBandRepository.saveAndFlush(updatedRole);

        return ResponseEntity.ok(new RoleInTheBandResponse(savedRole));
    }


}
