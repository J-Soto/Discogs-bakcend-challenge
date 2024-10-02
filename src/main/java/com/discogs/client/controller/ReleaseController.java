package com.discogs.client.controller;

import com.discogs.client.dto.internal.ReleaseDTO;
import com.discogs.client.service.ReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing releases.
 */
@RestController
@RequestMapping("/api/releases")
@Tag(name = "Release Controller", description = "API for managing releases")
public class ReleaseController {

    private final ReleaseService releaseService;

    /**
     * Constructs a new ReleaseController with the specified ReleaseService.
     *
     * @param releaseService the release service.
     */
    @Autowired
    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    /**
     * Retrieves all releases from the database.
     *
     * @return a ResponseEntity containing the list of all releases and HTTP status OK.
     */
    @Operation(summary = "Retrieve all releases")
    @GetMapping
    public ResponseEntity<List<ReleaseDTO>> getAllReleases() {
        List<ReleaseDTO> releases = releaseService.getAllReleases();
        return new ResponseEntity<>(releases, HttpStatus.OK);
    }

    /**
     * Retrieves a release by its title.
     *
     * @param title the title of the release.
     * @return a ResponseEntity containing the release if found, or HTTP status NOT FOUND if not found.
     */
    @Operation(summary = "Retrieve a release by title")
    @GetMapping("/{title}")
    public ResponseEntity<ReleaseDTO> getReleaseByTitle(@PathVariable String title) {
        Optional<ReleaseDTO> release = releaseService.getReleaseByTitle(title);
        return release.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a release by its title.
     *
     * @param title the title of the release to delete.
     * @return a ResponseEntity with HTTP status NO CONTENT, or HTTP status NOT FOUND if the release does not exist.
     */
    @Operation(summary = "Delete a release by title")
    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteRelease(@PathVariable String title) {
        try {
            releaseService.deleteRelease(title);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}