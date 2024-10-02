package com.discogs.client.dto.internal;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * DTO for master information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MasterDTO {
    @NotNull(message = "Discogs ID is mandatory")
    private Long id;

    @NotBlank(message = "Title cannot be empty.")
    private String title;

    private String country;

    private String year;

    private List<String> format;

    private List<String> label;

    private List<String> genre;

    @NotNull(message = "Style cannot be null")
    private List<String> style;

    private String thumb;

    private String cover_image;

    private String resource_url;

    private Long artistId;
}