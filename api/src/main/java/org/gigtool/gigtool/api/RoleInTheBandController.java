package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.RoleInTheBandService;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandCreate;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gig/band/roleintheband")
public class RoleInTheBandController {

    public final RoleInTheBandService roleInTheBandService;

    public RoleInTheBandController( RoleInTheBandService roleInTheBandService ){
        this.roleInTheBandService = roleInTheBandService;
    }

    @PostMapping
    public ResponseEntity<RoleInTheBandResponse> addRoleInTheBand( @RequestBody RoleInTheBandCreate roleInTheBandCreate ) {
        return roleInTheBandService.addRoleInTheBand( roleInTheBandCreate );
    }

    @GetMapping
    public ResponseEntity<List<RoleInTheBandResponse>> allRoleInTheBand(){
        return this.roleInTheBandService.getAllRolesInTheBand();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleInTheBandResponse> getRoleInTheBandByID( @PathVariable UUID id ){
        return this.roleInTheBandService.getRoleInTheBandById( id );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleInTheBandResponse> updateRoleInTheBand( @PathVariable UUID id, @RequestBody RoleInTheBandCreate roleRequest ) {
        return this.roleInTheBandService.updateRoleInTheBand( id, roleRequest );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleInTheBand( @PathVariable UUID id ) {
        return this.roleInTheBandService.deleteRoleInTheBand( id );
    }
}
