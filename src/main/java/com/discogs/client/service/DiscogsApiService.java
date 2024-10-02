package com.discogs.client.service;

import com.discogs.client.dto.internal.ArtistDTO;
import com.discogs.client.dto.internal.MasterDTO;
import com.discogs.client.dto.internal.ReleaseDTO;
import com.discogs.client.dto.response.DiscogsArtistResponse;
import com.discogs.client.dto.response.DiscogsMastersResponse;
import com.discogs.client.dto.response.DiscogsReleasesResponse;
import com.discogs.client.exception.DiscogsApiException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service class for interacting with the Discogs API.
 */
@Service
public class DiscogsApiService {

    @Value("${discogs.api.baseUrl}")
    private String baseUrl;

    @Value("${discogs.api.token}")
    private String token;

    private final RestTemplate restTemplate;

    /**
     * Constructs a new DiscogsApiService with the specified RestTemplate.
     *
     * @param restTemplate the RestTemplate to use for API calls.
     */
    @Autowired
    public DiscogsApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves the Discogs ID of an artist by their name.
     *
     * @param artistName the name of the artist.
     * @return the Discogs ID of the artist.
     * @throws DiscogsApiException if the artist is not found or there is an error communicating with the API.
     */
    public Long getArtistIdByName(@NotNull String artistName) {
        String url = String.format("%s/database/search?q=%s&type=artist&token=%s", baseUrl, artistName, token);
        try {
            DiscogsArtistResponse response = restTemplate.getForObject(url, DiscogsArtistResponse.class);
            if (response == null || response.getResults().isEmpty()) {
                throw new DiscogsApiException("No se encontraron resultados para el artista.", HttpStatus.NOT_FOUND);
            }
            return response.getResults().get(0).getId();
        } catch (RestClientException e) {
            throw new DiscogsApiException("Error al comunicar con la API de Discogs.", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Retrieves the details of an artist by their Discogs ID.
     *
     * @param discogsId the Discogs ID of the artist.
     * @return the details of the artist.
     * @throws DiscogsApiException if the artist details are not found or there is an error communicating with the API.
     */
    public ArtistDTO getArtistDetailsById(@NotNull Long discogsId) {
        String url = String.format("%s/artists/%d?token=%s", baseUrl, discogsId, token);
        try {
            ArtistDTO artist = restTemplate.getForObject(url, ArtistDTO.class);
            if (artist == null) {
                throw new DiscogsApiException("Detalles del artista no encontrados.", HttpStatus.NOT_FOUND);
            }
            return artist;
        } catch (RestClientException e) {
            throw new DiscogsApiException("Error al comunicar con la API de Discogs.", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Retrieves the master releases of an artist by their name.
     *
     * @param artistName the name of the artist.
     * @return a list of master releases.
     * @throws DiscogsApiException if there is an error communicating with the API.
     */
    public List<MasterDTO> getArtistMasters(@NotNull String artistName) {
        String url = String.format("%s/database/search?artist=%s&type=master&token=%s", baseUrl, artistName, token);
        try {
            DiscogsMastersResponse response = restTemplate.getForObject(url, DiscogsMastersResponse.class);
            return response != null ? response.getResults() : List.of();
        } catch (RestClientException e) {
            throw new DiscogsApiException("Error al comunicar con la API de Discogs.", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Retrieves the releases of an artist by their name.
     *
     * @param artistName the name of the artist.
     * @return a list of releases.
     * @throws DiscogsApiException if there is an error communicating with the API.
     */
    public List<ReleaseDTO> getArtistReleases(@NotNull String artistName) {
        String url = String.format("%s/database/search?artist=%s&type=release&token=%s", baseUrl, artistName, token);
        try {
            DiscogsReleasesResponse response = restTemplate.getForObject(url, DiscogsReleasesResponse.class);
            return response != null ? response.getResults() : List.of();
        } catch (RestClientException e) {
            throw new DiscogsApiException("Error al comunicar con la API de Discogs.", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}