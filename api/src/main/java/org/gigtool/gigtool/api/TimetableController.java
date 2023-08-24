package org.gigtool.gigtool.api;

import org.gigtool.gigtool.storage.model.Happening;
import org.gigtool.gigtool.storage.services.TimetableService;
import org.gigtool.gigtool.storage.services.model.CalcResponse;
import org.gigtool.gigtool.storage.services.model.LocationResponse;
import org.gigtool.gigtool.storage.services.model.TimetableResponse;
import org.gigtool.gigtool.storage.services.model.whereismyequipmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/search")
    public ResponseEntity<List<TimetableResponse>> getFilteredHappenings(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        return this.timetableService.getFilteredHappenings(name, description);
    }

    @GetMapping("/{id}/calc")
    public ResponseEntity<CalcResponse> getTotalValuesOfHappening(@PathVariable UUID id) {
        return this.timetableService.getTotalValuesOfHappening(id);
    }

    @GetMapping("/{id}/whereismyequipment")
    public ResponseEntity<List<whereismyequipmentResponse>> whereIsMyEquipment(@PathVariable UUID id) {
        return this.timetableService.getLocationsOfEquiptmentFromHappening(id);
    }

}
