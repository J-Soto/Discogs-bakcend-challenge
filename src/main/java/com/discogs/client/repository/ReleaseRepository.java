package com.discogs.client.repository;

import com.discogs.client.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Release entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {

    /**
     * Finds a release by its title.
     *
     * @param title the title of the release.
     * @return an Optional containing the release if found, or empty if not found.
     */
    Optional<Release> findByTitle(String title);

    /**
     * Checks if a release exists by its title.
     *
     * @param title the title of the release.
     * @return true if a release with the specified title exists, false otherwise.
     */
    boolean existsByTitle(String title);

    /**
     * Deletes a release by its title.
     *
     * @param title the title of the release to delete.
     */
    void deleteByTitle(String title);
}