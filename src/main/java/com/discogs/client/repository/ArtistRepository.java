package com.discogs.client.repository;

import com.discogs.client.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Artist entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    /**
     * Finds an artist by its name.
     *
     * @param name the name of the artist.
     * @return an Optional containing the artist if found, or empty if not found.
     */
    Optional<Artist> findByName(String name);

    /**
     * Checks if an artist exists by its name.
     *
     * @param name the name of the artist.
     * @return true if an artist with the specified name exists, false otherwise.
     */
    boolean existsByName(String name);

    /**
     * Deletes an artist by its name.
     *
     * @param name the name of the artist to delete.
     */
    void deleteByName(String name);
}