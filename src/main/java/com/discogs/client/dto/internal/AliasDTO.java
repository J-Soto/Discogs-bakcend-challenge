package com.discogs.client.dto.internal;

import lombok.*;
import jakarta.validation.constraints.*;

/**
 * DTO for alias information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AliasDTO {
    @NotNull(message = "Discogs ID is mandatory")
    private Long id;

    @NotBlank(message = "Alias name is mandatory")
    private String name;

    private String resource_url;

    private Long artistId;
}