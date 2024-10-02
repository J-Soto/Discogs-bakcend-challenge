package com.discogs.client.dto.internal;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * DTO for artist information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArtistDTO {
    @NotNull(message = "Discogs ID is mandatory")
    private Long id;

    @NotBlank(message = "Artist name is mandatory")
    private String name;

    private String realname;
    private String profile;
    private String resource_url;
    private String uri;
    private String releases_url;
    private List<String> namevariations;
    private List<String> urls;
    private List<AliasDTO> aliases;
    private List<MasterDTO> masters;
    private List<ReleaseDTO> releases;
}