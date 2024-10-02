package com.discogs.client.model;

import lombok.*;

/**
 * Entity for pagination information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
    private int page;
    private int pages;
    private int per_page;
    private int items;
    private Object urls;
}