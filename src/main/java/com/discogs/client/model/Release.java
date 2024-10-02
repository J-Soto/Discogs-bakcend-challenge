package com.discogs.client.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * Entity for release information.
 */
@Entity
@Table(name = "releases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"artist"})
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Discogs ID is mandatory")
    private Long discogsId;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String country;

    private String year;

    @ElementCollection
    private List<String> format;

    @ElementCollection
    private List<String> label;

    @ElementCollection
    private List<String> genre;

    @ElementCollection
    private List<String> style;

    private String thumb;

    private String coverImage;

    private String resourceUrl;

    private Long master_id;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
}