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

/**
 * Service class for managing band roles in the application.
 */
@Service
public class RoleInTheBandService {

    private final RoleInTheBandRepository roleInTheBandRepository;

    public RoleInTheBandService (RoleInTheBandRepository roleInTheBandRepository) {
        this.roleInTheBandRepository = roleInTheBandRepository;
    }

    /**
     * Adds a new role in the band to the system.
     *
     * @param roleInTheBandCreate The information for creating the new role in the band.
     * @return A response entity indicating the success or failure of the role addition operation.
     */
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

    /**
     * Retrieves a list of all roles in the band present in the system.
     *
     * @return A response entity containing a list of role in the band responses.
     */
    public ResponseEntity<List<RoleInTheBandResponse>> getAllRolesInTheBand() {

        List<RoleInTheBand> roleList = roleInTheBandRepository.findAll();

        List<RoleInTheBandResponse> responseList = roleList.stream()
                .map(RoleInTheBandResponse::new)
                .toList();

        return ResponseEntity.ok().body(responseList);
    }

    /**
     * Retrieves detailed information about a role in the band based on its unique identifier.
     *
     * @param roleId The unique identifier of the role in the band to retrieve information for.
     * @return A response entity containing the retrieved role in the band response.
     */
    public ResponseEntity<RoleInTheBandResponse> getRoleInTheBandById(UUID roleId) {

        Optional<RoleInTheBand> roleOptional = roleInTheBandRepository.findById(roleId);

        return roleOptional.map(roleInTheBand -> ResponseEntity.ok(new RoleInTheBandResponse(roleInTheBand)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing role in the band with new information.
     *
     * @param roleId     The unique identifier of the role in the band to be updated.
     * @param roleRequest An object containing the role's updated information.
     * @return A response entity indicating the success or failure of the role update operation.
     */
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

    /**
     * Deletes a role in the band from the system, along with its associated data, given its unique identifier.
     *
     * @param roleId The unique identifier of the role in the band to be deleted.
     * @return A response entity indicating the success or failure of the role deletion operation.
     */
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
