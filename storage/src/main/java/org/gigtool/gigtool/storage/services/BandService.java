package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.repositories.BandRepository;
import org.springframework.stereotype.Service;

@Service
public class BandService {

    private final BandRepository bandRepository;

    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }




}
