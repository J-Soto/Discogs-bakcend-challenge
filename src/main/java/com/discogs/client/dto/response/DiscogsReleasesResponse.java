package com.discogs.client.dto.response;

import com.discogs.client.dto.internal.ReleaseDTO;
import com.discogs.client.model.Pagination;
import lombok.*;
import java.util.List;

/**
 * DTO for Discogs releases response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiscogsReleasesResponse {
    private List<ReleaseDTO> results;
    private Pagination pagination;
}