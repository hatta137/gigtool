package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.RoleInTheBandService;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandCreate;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gig/band/role")
public class RoleInTheBandController {

    public final RoleInTheBandService roleInTheBandService;

    public RoleInTheBandController(RoleInTheBandService roleInTheBandService){
        this.roleInTheBandService = roleInTheBandService;
    }

    @PostMapping
    public ResponseEntity<RoleInTheBandResponse> addRoleInTheBand(@RequestBody RoleInTheBandCreate roleInTheBandCreate) {
        return roleInTheBandService.addRoleInTheBand(roleInTheBandCreate);
    }
}
