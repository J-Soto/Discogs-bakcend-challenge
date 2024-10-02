package com.discogs.client.service;

import com.discogs.client.dto.internal.ArtistDTO;
import com.discogs.client.dto.internal.MasterDTO;
import com.discogs.client.dto.internal.ReleaseDTO;
import com.discogs.client.exception.DiscogsApiException;
import com.discogs.client.model.*;
import com.discogs.client.repository.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing search-related operations.
 */
@Service
public class SearchService {

    private final DiscogsApiService discogsApiService;
    private final ArtistRepository artistRepository;
    private final MasterRepository masterRepository;
    private final ReleaseRepository releaseRepository;

    /**
     * Constructs a new SearchService with the specified dependencies.
     *
     * @param discogsApiService the Discogs API service.
     * @param artistRepository the artist repository.
     * @param masterRepository the master repository.
     * @param releaseRepository the release repository.
     */
    @Autowired
    public SearchService(DiscogsApiService discogsApiService, ArtistRepository artistRepository,
                         MasterRepository masterRepository, ReleaseRepository releaseRepository) {
        this.discogsApiService = discogsApiService;
        this.artistRepository = artistRepository;
        this.masterRepository = masterRepository;
        this.releaseRepository = releaseRepository;
    }

    /**
     * Searches for an artist by name and saves the artist information in the database.
     *
     * @param artistName the name of the artist to search for.
     * @throws IllegalArgumentException if the artist name is empty or null.
     * @throws DiscogsApiException if the artist is not found in the Discogs API.
     */
    @Transactional
    public void searchAndSaveArtist(@NotNull String artistName) {
        validateArtistName(artistName);

        if (artistRepository.existsByName(artistName)) {
            return;
        }

        Long artistId = discogsApiService.getArtistIdByName(artistName);
        if (artistId == null) {
            throw new DiscogsApiException("Artist: " + artistName + " not found", HttpStatus.NOT_FOUND);
        }

        ArtistDTO artistDTO = discogsApiService.getArtistDetailsById(artistId);
        Artist artist = new Artist();
        artist.setDiscogsId(artistId);
        artist.setName(Optional.ofNullable(artistDTO.getName()).orElse(""));
        artist.setRealname(Optional.ofNullable(artistDTO.getRealname()).orElse(""));
        artist.setProfile(Optional.ofNullable(artistDTO.getProfile()).orElse(""));
        artist.setResourceUrl(Optional.ofNullable(artistDTO.getResource_url()).orElse(""));
        artist.setUri(Optional.ofNullable(artistDTO.getUri()).orElse(""));
        artist.setReleasesUrl(Optional.ofNullable(artistDTO.getReleases_url()).orElse(""));
        artist.setNameVariations(Optional.ofNullable(artistDTO.getNamevariations()).orElse(new ArrayList<>()));
        artist.setAliases(Optional.ofNullable(artistDTO.getAliases()).orElse(new ArrayList<>()).stream()
                .map(aliasDTO -> new Alias(null, aliasDTO.getId(), aliasDTO.getName(), aliasDTO.getResource_url(), artist))
                .collect(Collectors.toList()));

        artistRepository.save(artist);

        List<MasterDTO> mastersDTO = discogsApiService.getArtistMasters(artistName);
        List<ReleaseDTO> releasesDTO = discogsApiService.getArtistReleases(artistName);

        if (mastersDTO != null) {
            for (MasterDTO masterDTO : mastersDTO) {
                Master master = new Master();
                master.setDiscogsId(Optional.ofNullable(masterDTO.getId()).orElse(0L));
                master.setTitle(Optional.ofNullable(masterDTO.getTitle()).orElse(""));
                master.setCountry(Optional.ofNullable(masterDTO.getCountry()).orElse(""));
                master.setYear(Optional.ofNullable(masterDTO.getYear()).orElse(""));
                master.setFormat(Optional.ofNullable(masterDTO.getFormat()).orElse(new ArrayList<>()));
                master.setLabel(Optional.ofNullable(masterDTO.getLabel()).orElse(new ArrayList<>()));
                master.setGenre(Optional.ofNullable(masterDTO.getGenre()).orElse(new ArrayList<>()));
                master.setStyle(Optional.ofNullable(masterDTO.getStyle()).orElse(new ArrayList<>()));
                master.setThumb(Optional.ofNullable(masterDTO.getThumb()).orElse(""));
                master.setCoverImage(Optional.ofNullable(masterDTO.getCover_image()).orElse(""));
                master.setResourceUrl(Optional.ofNullable(masterDTO.getResource_url()).orElse(""));
                master.setArtist(artist);
                masterRepository.save(master);
            }
        }

        if (releasesDTO != null) {
            for (ReleaseDTO releaseDTO : releasesDTO) {
                Release release = new Release();
                release.setDiscogsId(Optional.ofNullable(releaseDTO.getId()).orElse(0L));
                release.setTitle(Optional.ofNullable(releaseDTO.getTitle()).orElse(""));
                release.setCountry(Optional.ofNullable(releaseDTO.getCountry()).orElse(""));
                release.setYear(Optional.ofNullable(releaseDTO.getYear()).orElse(""));
                release.setFormat(Optional.ofNullable(releaseDTO.getFormat()).orElse(new ArrayList<>()));
                release.setLabel(Optional.ofNullable(releaseDTO.getLabel()).orElse(new ArrayList<>()));
                release.setGenre(Optional.ofNullable(releaseDTO.getGenre()).orElse(new ArrayList<>()));
                release.setStyle(Optional.ofNullable(releaseDTO.getStyle()).orElse(new ArrayList<>()));
                release.setThumb(Optional.ofNullable(releaseDTO.getThumb()).orElse(""));
                release.setMaster_id(Optional.ofNullable(releaseDTO.getMaster_id()).orElse(0L));
                release.setCoverImage(Optional.ofNullable(releaseDTO.getCover_image()).orElse(""));
                release.setResourceUrl(Optional.ofNullable(releaseDTO.getResource_url()).orElse(""));
                release.setArtist(artist);
                releaseRepository.save(release);
            }
        }
    }

    /**
     * Validates the artist name.
     *
     * @param artistName the name of the artist to validate.
     * @throws IllegalArgumentException if the artist name is empty or null.
     */
    private void validateArtistName(@NotNull String artistName) {
        if (artistName == null || artistName.trim().isEmpty()) {
            throw new IllegalArgumentException("The artist name cannot be empty.");
        }
    }


}