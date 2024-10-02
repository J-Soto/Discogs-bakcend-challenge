package com.discogs.client.dto.response;

import com.discogs.client.dto.internal.ArtistDTO;
import com.discogs.client.model.Pagination;
import lombok.*;

import java.util.List;

/**
 * DTO for Discogs artist response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiscogsArtistResponse {
    private List<ArtistDTO> results;
    private Pagination pagination;
}