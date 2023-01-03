package com.cogniteq.wookiemovies.controller;

import com.cogniteq.wookiemovies.model.Movie;
import com.cogniteq.wookiemovies.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json")
@AllArgsConstructor
@Slf4j
public class MovieController {
    private MovieService movieService;

    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.OPTIONS})
    @GetMapping("/movies/{slug}")
    public ResponseEntity<Movie> getMovieBySlug(@PathVariable("slug") String slug) {
        return new ResponseEntity<>(movieService.getMovieBySlug(slug), HttpStatus.OK);
    }

    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.OPTIONS})
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMoviesBySearchTerm(
            @RequestParam(value = "q", required = false) String searchTerm) {
        return new ResponseEntity<>(movieService.getMoviesBySearchTerm(searchTerm), HttpStatus.OK);
    }

    @GetMapping(value = "/static/{folderName}/{fileName}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(@PathVariable("folderName") String folderName,
                                                       @PathVariable("fileName") String fileName) {

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(movieService.getMovieImageByLink(folderName, fileName)));

    }


    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}
