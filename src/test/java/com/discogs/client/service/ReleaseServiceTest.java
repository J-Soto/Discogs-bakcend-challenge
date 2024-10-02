package com.discogs.client.service;

import com.discogs.client.model.Release;
import com.discogs.client.repository.ReleaseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class for the ReleaseService.
 */
public class ReleaseServiceTest {

    @Mock
    private ReleaseRepository releaseRepository;

    @InjectMocks
    private ReleaseService releaseService;

    public ReleaseServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify that a release can be saved.
     */
    @Test
    public void testSaveRelease() {
        // Given
        Release release = new Release();
        release.setTitle("Test Release");
        when(releaseRepository.save(release)).thenReturn(release);

        // When
        Release savedRelease = releaseService.saveRelease(release);

        // Then
        assertThat(savedRelease.getTitle()).isEqualTo("Test Release");
    }
}