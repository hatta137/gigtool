package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.BandService;
import org.gigtool.gigtool.storage.services.model.BandCreate;
import org.gigtool.gigtool.storage.services.model.BandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gig/band")
public class BandController {

    public final BandService bandService;

    public BandController (BandService bandService){
        this.bandService = bandService;
    }

    @PostMapping
    public ResponseEntity<BandResponse> createBand(@RequestBody BandCreate bandCreate){
        return this.bandService.createBand(bandCreate);
    }
}
