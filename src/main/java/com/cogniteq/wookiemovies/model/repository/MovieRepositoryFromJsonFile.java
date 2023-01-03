package com.cogniteq.wookiemovies.model.repository;

import com.cogniteq.wookiemovies.model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Component
public class MovieRepositoryFromJsonFile {
    private final static String DATA_PATH = "/data/movies1.json";
    private List<Movie> movies = new ArrayList<>();

    @PostConstruct
    public void prepareMoviesData() {
        InputStream inputStream = this.getClass()
                .getResourceAsStream(DATA_PATH);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            movies.addAll(objectMapper.readValue(inputStream, new TypeReference<List<Movie>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Movie> getMovieById(UUID id) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst();
    }


    public Optional<Movie> getMovieBySlug(String slug) {
        return movies.stream()
                .filter(movie -> movie.getSlug().equals(slug))
                .findFirst();
    }

    public List<Movie> getMoviesBySearchTerm(String searchTerm) {
        return movies.stream()
                .filter(movie -> movie.getTitle().toUpperCase(Locale.ROOT).contains(searchTerm.toUpperCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }
}
