package com.cogniteq.wookiemovies.service;

import com.cogniteq.wookiemovies.exception.RequestParametersValidationException;
import com.cogniteq.wookiemovies.model.Movie;
import com.cogniteq.wookiemovies.model.repository.MovieRepositoryFromJsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    private final static String JPG = ".jpg";

    private MovieRepositoryFromJsonFile movieRepositoryFromJsonFile;

    public List<Movie> getMovies() {
        return movieRepositoryFromJsonFile.getMovies();
    }

    public Movie getMovieBySlug(String slug) {
        return movieRepositoryFromJsonFile.getMovieBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Movie with slug [%s] not found!", slug)));
    }

    public List<Movie> getMoviesBySearchTerm(String searchTerm) {
        if (StringUtils.isEmpty(searchTerm)) {
            return getMovies();
        }
        var movies = movieRepositoryFromJsonFile.getMoviesBySearchTerm(searchTerm);
        if (!CollectionUtils.isEmpty(movies)) {
            return movies;
        }
        throw new EntityNotFoundException(String.format("No movies with title [%s] found!", searchTerm));
    }

    public InputStream getMovieImageByLink(String folderName, String fileName) {
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(folderName)) {
            throw new RequestParametersValidationException(String.format("Folder name [%s] or fileName [%s] are empty!",
                    folderName, fileName));
        }

        if (!StringUtils.endsWithIgnoreCase(fileName, JPG)) {
            throw new RequestParametersValidationException(String.format("FileName [%s] parameter has wrong format!",
                    fileName));
        }
        UUID id;
        try {
            id = UUID.fromString(fileName.replace(JPG, org.apache.commons.lang3.StringUtils.EMPTY));
        } catch (IllegalArgumentException e) {
            throw new RequestParametersValidationException(
                    String.format("No movies with poster file name [%s] found!", fileName));
        }

        if (movieRepositoryFromJsonFile.getMovieById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("No movies with poster file name [%s].jpg found!", id));
        }

        return getClass().getResourceAsStream(createPath(folderName, fileName));
    }

    private String createPath(String folderName, String fileName) {
        return "/static/".concat(folderName).concat("/").concat(fileName);
    }

    @Autowired
    public void setMovieRepositoryFromJsonFile(MovieRepositoryFromJsonFile movieRepositoryFromJsonFile) {
        this.movieRepositoryFromJsonFile = movieRepositoryFromJsonFile;
    }

}
