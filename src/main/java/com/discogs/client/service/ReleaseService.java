package com.discogs.client.service;

import com.discogs.client.dto.internal.ReleaseDTO;
import com.discogs.client.model.Release;
import com.discogs.client.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing release-related operations.
 */
@Service
public class ReleaseService {

    private final ReleaseRepository releaseRepository;

    /**
     * Constructs a new ReleaseService with the specified dependencies.
     *
     * @param releaseRepository the release repository.
     */
    @Autowired
    public ReleaseService(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    /**
     * Retrieves all releases from the database.
     *
     * @return a list of all releases as ReleaseDTO.
     */
    public List<ReleaseDTO> getAllReleases() {
        return releaseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a release by its title.
     *
     * @param title the title of the release.
     * @return an Optional containing the release as ReleaseDTO if found, or empty if not found.
     */
    public Optional<ReleaseDTO> getReleaseByTitle(String title) {
        return releaseRepository.findByTitle(title)
                .map(this::convertToDTO);
    }

    /**
     * Deletes a release by its title.
     *
     * @param title the title of the release to delete.
     * @throws IllegalArgumentException if the release with the specified title does not exist.
     */
    public void deleteRelease(String title) {
        if (!releaseRepository.existsByTitle(title)) {
            throw new IllegalArgumentException("Release with title " + title + " does not exist.");
        }
        releaseRepository.deleteByTitle(title);
    }

    /**
     * Saves a release to the database.
     *
     * @param release the release entity to save.
     * @return the saved release entity.
     */
    public Release saveRelease(Release release) {
        return releaseRepository.save(release);
    }

    /**
     * Converts a Release entity to a ReleaseDTO.
     *
     * @param release the release entity.
     * @return the release DTO.
     */
    private ReleaseDTO convertToDTO(Release release) {
        return ReleaseDTO.builder()
                .id(release.getId())
                .title(release.getTitle())
                .artistId(release.getArtist().getId())
                .year(release.getYear())
                .genre(release.getGenre())
                .style(release.getStyle())
                .thumb(release.getThumb())
                .cover_image(release.getCoverImage())
                .resource_url(release.getResourceUrl())
                .build();
    }
}