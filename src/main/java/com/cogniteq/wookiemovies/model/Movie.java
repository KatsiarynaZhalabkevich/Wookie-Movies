package com.cogniteq.wookiemovies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Movie {
    private UUID id;
    private String backdrop;
    private List<String> cast;
    private String classification;
    private List<String> director;
    private List<String> genres; //ENUM
    @JsonProperty("imdb_rating")
    private double imdbRating;
    private String length;
    private String overview;
    private String poster;
    @JsonProperty("released_on")
    private Date releasedOn;
    private String slug;
    private String title;
}
