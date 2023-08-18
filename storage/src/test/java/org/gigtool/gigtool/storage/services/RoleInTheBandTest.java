package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.services.model.RoleInTheBandCreate;
import org.gigtool.gigtool.storage.services.model.RoleInTheBandResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class RoleInTheBandTest {

    @Autowired
    private RoleInTheBandService roleInTheBandService;

    @Autowired
    private TestUtils testUtils;

    private RoleInTheBandCreate roleInTheBandToSave;

    private ResponseEntity<RoleInTheBandResponse> savedRoleInTheBand;

    private UUID savedRoleInTheBandId;

    @BeforeEach
    private void setup() {

    }
}
