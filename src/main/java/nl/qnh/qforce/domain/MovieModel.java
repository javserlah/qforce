package nl.qnh.qforce.domain;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

/**
 * Created by Javi on 21/11/2017.
 * Implements the movie interface for retrieving movie details
 */
public class MovieModel implements Movie {
    String title;

    Integer episode;

    String director;

    LocalDate releaseDate;

    public MovieModel() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Integer getEpisode() {
        return this.episode;
    }

    @Override
    public String getDirector() {
        return this.director;
    }

    @Override
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }
}
