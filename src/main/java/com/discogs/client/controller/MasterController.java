package com.discogs.client.controller;

import com.discogs.client.dto.internal.MasterDTO;
import com.discogs.client.service.MasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing masters.
 */
@RestController
@RequestMapping("/api/masters")
@Tag(name = "Master Controller", description = "API for managing masters")
public class MasterController {

    private final MasterService masterService;

    /**
     * Constructs a new MasterController with the specified MasterService.
     *
     * @param masterService the master service.
     */
    @Autowired
    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    /**
     * Retrieves all masters from the database.
     *
     * @return a ResponseEntity containing the list of all masters and HTTP status OK.
     */
    @Operation(summary = "Retrieve all masters")
    @GetMapping
    public ResponseEntity<List<MasterDTO>> getAllMasters() {
        List<MasterDTO> masters = masterService.getAllMasters();
        return new ResponseEntity<>(masters, HttpStatus.OK);
    }

    /**
     * Retrieves a master by its title.
     *
     * @param title the title of the master.
     * @return a ResponseEntity containing the master if found, or HTTP status NOT FOUND if not found.
     */
    @Operation(summary = "Retrieve a master by title")
    @GetMapping("/{title}")
    public ResponseEntity<MasterDTO> getMasterByTitle(@PathVariable String title) {
        Optional<MasterDTO> master = masterService.getMasterByTitle(title);
        return master.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a master by its title.
     *
     * @param title the title of the master to delete.
     * @return a ResponseEntity with HTTP status NO CONTENT, or HTTP status NOT FOUND if the master does not exist.
     */
    @Operation(summary = "Delete a master by title")
    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteMaster(@PathVariable String title) {
        try {
            masterService.deleteMaster(title);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}