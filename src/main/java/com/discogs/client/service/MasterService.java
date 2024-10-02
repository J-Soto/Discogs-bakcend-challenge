package com.discogs.client.service;

import com.discogs.client.dto.internal.MasterDTO;
import com.discogs.client.model.Master;
import com.discogs.client.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing master-related operations.
 */
@Service
public class MasterService {

    private final MasterRepository masterRepository;

    /**
     * Constructs a new MasterService with the specified dependencies.
     *
     * @param masterRepository the master repository.
     */
    @Autowired
    public MasterService(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    /**
     * Retrieves all masters from the database.
     *
     * @return a list of all masters as MasterDTO.
     */
    public List<MasterDTO> getAllMasters() {
        return masterRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a master by its title.
     *
     * @param title the title of the master.
     * @return an Optional containing the master as MasterDTO if found, or empty if not found.
     */
    public Optional<MasterDTO> getMasterByTitle(String title) {
        return masterRepository.findByTitle(title)
                .map(this::convertToDTO);
    }

    /**
     * Deletes a master by its title.
     *
     * @param title the title of the master to delete.
     * @throws IllegalArgumentException if the master with the specified title does not exist.
     */
    public void deleteMaster(String title) {
        if (!masterRepository.existsByTitle(title)) {
            throw new IllegalArgumentException("Master with title " + title + " does not exist.");
        }
        masterRepository.deleteByTitle(title);
    }

    /**
     * Saves a master to the database.
     *
     * @param master the master entity to save.
     * @return the saved master entity.
     */
    public Master saveMaster(Master master) {
        return masterRepository.save(master);
    }


    /**
     * Converts a Master entity to a MasterDTO.
     *
     * @param master the master entity.
     * @return the master DTO.
     */
    private MasterDTO convertToDTO(Master master) {
        return MasterDTO.builder()
                .id(master.getDiscogsId())
                .title(master.getTitle())
                .country(master.getCountry())
                .year(master.getYear())
                .format(master.getFormat())
                .label(master.getLabel())
                .genre(master.getGenre())
                .style(master.getStyle())
                .thumb(master.getThumb())
                .cover_image(master.getCoverImage())
                .resource_url(master.getResourceUrl())
                .artistId(master.getArtist().getId())
                .build();
    }
}