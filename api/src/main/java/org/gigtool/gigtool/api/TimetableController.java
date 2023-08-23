package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.services.TimetableService;
import org.gigtool.gigtool.storage.services.model.TimetableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping
    public ResponseEntity<List<TimetableResponse>> getAllHappenings() {
        return this.timetableService.getAll();
    }

}
