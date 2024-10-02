package com.discogs.client.service;

import com.discogs.client.dto.response.ArtistComparisonResponse;
import com.discogs.client.exception.ResourceNotFoundException;
import com.discogs.client.model.Artist;
import com.discogs.client.model.Release;
import com.discogs.client.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for comparing artists.
 */
@Service
public class ComparisonService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SearchService searchService;

    /**
     * Compares a list of artists by their names.
     *
     * @param artistNames the list of artist names to compare.
     * @return a list of ArtistComparisonResponse objects containing the comparison results.
     * @throws ResourceNotFoundException if an artist is not found.
     */
    public List<ArtistComparisonResponse> compareArtists(List<String> artistNames) {
        List<Artist> artists = new ArrayList<>();

        for (String artistName : artistNames) {
            Optional<Artist> optionalArtist = artistRepository.findByName(artistName);
            Artist artist;
            if (optionalArtist.isPresent()) {
                artist = optionalArtist.get();
            } else {
                throw new ResourceNotFoundException("Artist not found: " + artistName);
            }
            artists.add(artist);
        }
        return artists.stream().map(this::createArtistComparisonDTO).collect(Collectors.toList());
    }

    /**
     * Creates an ArtistComparisonResponse object for a given artist.
     *
     * @param artist the artist to create the comparison response for.
     * @return the ArtistComparisonResponse object.
     */
    private ArtistComparisonResponse createArtistComparisonDTO(Artist artist) {
        List<Release> releases = Optional.ofNullable(artist.getReleases()).orElse(Collections.emptyList());
        int numberOfReleases = releases.size();
        int activeYears = (numberOfReleases > 0) ? calculateActiveYears(releases) : 0;
        String firstReleaseYear = findFirstReleaseYear(releases);
        String lastReleaseYear = findLastReleaseYear(releases);
        String mostCommonGenre = findMostCommonGenre(releases);
        List<String> commonGenres = findCommonGenres(releases);

        return ArtistComparisonResponse.builder()
                .artistName(artist.getName())
                .numberOfReleases(numberOfReleases)
                .activeYears(activeYears)
                .firstReleaseYear(firstReleaseYear)
                .lastReleaseYear(lastReleaseYear)
                .mostCommonGenre(mostCommonGenre)
                .commonGenres(commonGenres)
                .build();
    }

    /**
     * Calculates the number of active years for a list of releases.
     *
     * @param releases the list of releases.
     * @return the number of active years.
     */
    private int calculateActiveYears(List<Release> releases) {
        List<Integer> years = releases.stream()
                .map(Release::getYear)
                .filter(year -> !year.isEmpty())
                .map(Integer::parseInt)
                .distinct()
                .toList();

        if (years.isEmpty()) {
            return 0;
        }

        return years.stream()
                .max(Integer::compareTo)
                .orElse(0) - years.stream()
                .min(Integer::compareTo)
                .orElse(0);
    }

    /**
     * Finds the first release year from a list of releases.
     *
     * @param releases the list of releases.
     * @return the first release year.
     */
    private String findFirstReleaseYear(List<Release> releases) {
        return releases.stream()
                .map(Release::getYear)
                .filter(year -> !year.isEmpty())
                .min(String::compareTo)
                .orElse("Unknown");
    }

    /**
     * Finds the last release year from a list of releases.
     *
     * @param releases the list of releases.
     * @return the last release year.
     */
    private String findLastReleaseYear(List<Release> releases) {
        return releases.stream()
                .map(Release::getYear)
                .filter(year -> !year.isEmpty())
                .max(String::compareTo)
                .orElse("Unknown");
    }

    /**
     * Finds the most common genre from a list of releases.
     *
     * @param releases the list of releases.
     * @return the most common genre.
     */
    private String findMostCommonGenre(List<Release> releases) {
        return releases.stream()
                .flatMap(release -> release.getGenre().stream())
                .collect(Collectors.groupingBy(genre -> genre, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }

    /**
     * Finds the common genres from a list of releases.
     *
     * @param releases the list of releases.
     * @return the list of common genres.
     */
    private List<String> findCommonGenres(List<Release> releases) {
        return releases.stream()
                .flatMap(release -> release.getGenre().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}