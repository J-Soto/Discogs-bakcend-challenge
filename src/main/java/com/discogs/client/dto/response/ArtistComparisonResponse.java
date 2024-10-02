package com.discogs.client.dto.response;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistComparisonResponse {

    @NotBlank(message = "Artist name cannot be empty")
    private String artistName;

    @NotNull(message = "Number of releases cannot be null")
    private int numberOfReleases;

    @NotNull(message = "Active years cannot be null")
    private int activeYears;

    private List<String> commonGenres;
    private String firstReleaseYear;
    private String lastReleaseYear;
    private String mostCommonGenre;

    @Override
    public String toString() {
        return String.format("ArtistComparisonDTO { artistName='%s', numberOfReleases=%d, activeYears=%d, " +
                             "firstReleaseYear='%s', lastReleaseYear='%s', mostCommonGenre='%s' }",
                             artistName, numberOfReleases, activeYears, firstReleaseYear, lastReleaseYear, mostCommonGenre);
    }
}

