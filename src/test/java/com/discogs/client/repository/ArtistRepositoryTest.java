package com.discogs.client.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.discogs.client.model.Artist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    public void testSaveAndFindArtist() {
        Artist artist = new Artist();
        artist.setDiscogsId(12345L);
        artist.setName("Test Artist");

        artistRepository.save(artist);

        Optional<Artist> foundArtist = artistRepository.findById(artist.getId());
        assertTrue(foundArtist.isPresent());
        assertEquals("Test Artist", foundArtist.get().getName());
    }
}