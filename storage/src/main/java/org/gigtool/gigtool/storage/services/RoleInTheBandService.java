package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.RoleInTheBand;
import org.gigtool.gigtool.storage.repositories.RoleInTheBandRepository;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandCreate;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandResponse;
import org.springframework.http.HttpStatus;
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

        if ((roleInTheBandCreate.getName() == null) || (roleInTheBandCreate.getDescription() == null)) {
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

    public ResponseEntity<RoleInTheBandResponse> updateRoleInTheBand(UUID roleId, RoleInTheBandCreate roleRequest) {

        if (roleId == null || roleRequest.getName() == null || roleRequest.getDescription() == null) {
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

    public ResponseEntity<String> deleteRoleInTheBand(UUID roleId) {

        if (roleId == null) {
            return ResponseEntity.badRequest().body("No ID");
        }

        Optional<RoleInTheBand> existingRole = roleInTheBandRepository.findById(roleId);

        if (existingRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int bandsWithRoleInTheBand = roleInTheBandRepository.countBandsWithRole(roleId);

        if(bandsWithRoleInTheBand > 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("RoleInTheBand has a relation to a Band. You cannot delete this RoleInTheBand!");
        }

        roleInTheBandRepository.delete(existingRole.get());

        return ResponseEntity.ok("RoleInTheBand is deleted");
    }


}
