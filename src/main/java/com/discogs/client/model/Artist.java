package com.discogs.client.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * Entity for artist information.
 */
@Entity
@Table(name = "artists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"aliases", "masters", "releases"})
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Discogs ID is mandatory")
    private Long discogsId;

    @NotNull(message = "Discogs ID is mandatory")
    private String name;

    private String realname;

    @Column(length = 2000)
    private String profile;

    private String resourceUrl;

    private String uri;

    private String releasesUrl;

    @ElementCollection
    private List<String> nameVariations;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Alias> aliases;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Master> masters;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Release> releases;
}