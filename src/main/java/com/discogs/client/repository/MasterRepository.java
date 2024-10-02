package com.discogs.client.repository;

import com.discogs.client.model.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Master entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {

    /**
     * Finds a master by its title.
     *
     * @param title the title of the master.
     * @return an Optional containing the master if found, or empty if not found.
     */
    Optional<Master> findByTitle(String title);

    /**
     * Checks if a master exists by its title.
     *
     * @param title the title of the master.
     * @return true if a master with the specified title exists, false otherwise.
     */
    boolean existsByTitle(String title);

    /**
     * Deletes a master by its title.
     *
     * @param title the title of the master to delete.
     */
    void deleteByTitle(String title);
}