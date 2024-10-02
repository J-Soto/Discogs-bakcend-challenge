package com.discogs.client.dto.response;

import com.discogs.client.dto.internal.MasterDTO;
import com.discogs.client.model.Pagination;
import lombok.*;

import java.util.List;

/**
 * DTO for Discogs masters response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiscogsMastersResponse {
    private List<MasterDTO> results;
    private Pagination pagination;
}