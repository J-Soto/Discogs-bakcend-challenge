package com.discogs.client.service;

import com.discogs.client.dto.response.ArtistComparisonResponse;
import com.discogs.client.model.Artist;
import com.discogs.client.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing artist-related operations.
 */
@Service
public class ArtistService {

    private final SearchService searchService;
    private final ComparisonService comparisonService;
    private final ArtistRepository artistRepository;

    /**
     * Constructs a new ArtistService with the specified dependencies.
     *
     * @param searchService the search service.
     * @param comparisonService the comparison service.
     * @param artistRepository the artist repository.
     */
    @Autowired
    public ArtistService(SearchService searchService, ComparisonService comparisonService, ArtistRepository artistRepository) {
        this.searchService = searchService;
        this.comparisonService = comparisonService;
        this.artistRepository = artistRepository;
    }

    /**
     * Searches for an artist by name and saves the artist information in the database.
     *
     * @param artistName the name of the artist to search for.
     * @throws IllegalArgumentException if the artist name is empty or null.
     */
    public void searchArtist(@NotEmpty(message = "The artist name cannot be empty.") String artistName) {
        searchService.searchAndSaveArtist(artistName);
    }

    /**
     * Compares multiple artists based on their names.
     *
     * @param artistNames a list of artist names to compare.
     * @return a list of ArtistComparisonDTO objects containing the comparison results.
     */
    public List<ArtistComparisonResponse> compareArtists(@NotEmpty(message = "The list of artist names cannot be empty.") List<String> artistNames) {
        return comparisonService.compareArtists(artistNames);
    }

    /**
     * Retrieves all artists from the database.
     *
     * @return a list of all artists.
     */
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    /**
     * Retrieves an artist by its name.
     *
     * @param name the name of the artist.
     * @return an Optional containing the artist if found, or empty if not found.
     */
    public Optional<Artist> getArtistByName(String name) {
        return artistRepository.findByName(name);
    }

    /**
     * Deletes an artist by its name.
     *
     * @param name the name of the artist to delete.
     * @throws IllegalArgumentException if the artist with the specified name does not exist.
     */
    public void deleteArtist(String name) {
        if (!artistRepository.existsByName(name)) {
            throw new IllegalArgumentException("Artist with name " + name + " does not exist.");
        }
        artistRepository.deleteByName(name);
    }
}