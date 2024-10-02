package com.discogs.client.controller;

import com.discogs.client.dto.response.ArtistComparisonResponse;
import com.discogs.client.model.Artist;
import com.discogs.client.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing artists.
 */
@RestController
@RequestMapping("/artists")
@Tag(name = "Artist Controller", description = "API for managing artists")
public class ArtistController {

    private final ArtistService artistService;

    /**
     * Constructs a new ArtistController with the specified ArtistService.
     *
     * @param artistService the artist service.
     */
    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    /**
     * Searches for an artist by name.
     *
     * @param name the name of the artist to search for.
     * @return a ResponseEntity with HTTP status OK.
     */
    @Operation(summary = "Search for an artist by name")
    @GetMapping("/search")
    public ResponseEntity<Void> searchArtist(@RequestParam String name) {
        artistService.searchArtist(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Compares multiple artists by their names.
     *
     * @param artistNames a list of artist names to compare.
     * @return a ResponseEntity containing the comparison results and HTTP status OK.
     */
    @Operation(summary = "Compare multiple artists by their names")
    @PostMapping("/compare")
    public ResponseEntity<List<ArtistComparisonResponse>> compareArtists(@RequestBody List<String> artistNames) {
        List<ArtistComparisonResponse> comparisonResults = artistService.compareArtists(artistNames);
        return new ResponseEntity<>(comparisonResults, HttpStatus.OK);
    }

    /**
     * Retrieves all artists from the database.
     *
     * @return a ResponseEntity containing the list of all artists and HTTP status OK.
     */
    @Operation(summary = "Retrieve all artists")
    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    /**
     * Retrieves an artist by its name.
     *
     * @param name the name of the artist.
     * @return a ResponseEntity containing the artist if found, or HTTP status NOT FOUND if not found.
     */
    @Operation(summary = "Retrieve an artist by name")
    @GetMapping("/{name}")
    public ResponseEntity<Artist> getArtistByName(@PathVariable String name) {
        Optional<Artist> artist = artistService.getArtistByName(name);
        return artist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes an artist by its name.
     *
     * @param name the name of the artist to delete.
     * @return a ResponseEntity with HTTP status NO CONTENT, or HTTP status NOT FOUND if the artist does not exist.
     */
    @Operation(summary = "Delete an artist by name")
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteArtist(@PathVariable String name) {
        try {
            artistService.deleteArtist(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}