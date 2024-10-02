package com.discogs.client.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

/**
 * Entity for alias information.
 */
@Entity
@Table(name = "aliases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Discogs ID is mandatory")
    private Long discogsId;

    @NotBlank(message = "Alias name is mandatory")
    private String name;

    private String resourceUrl;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
}
