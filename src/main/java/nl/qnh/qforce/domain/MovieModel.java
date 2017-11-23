package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.tomcat.jni.Local;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Javi on 21/11/2017.
 * Implements the movie interface for retrieving movie details
 */
@JsonPropertyOrder({"title","episode","director","releaseDate"})
public class MovieModel implements Movie {
    private String title;

    private Integer episode;

    private String director;

    private LocalDate releaseDate;

    public MovieModel() {
    }

    @JsonCreator
    public MovieModel(@JsonProperty("title") String title, @JsonProperty("episode_id") Integer episode, @JsonProperty("director") String director, @JsonProperty("release_date") String releaseDate) {
        this.title = title;
        this.episode = episode;
        this.director = director;
        this.releaseDate = formatReleaseDate(releaseDate);
    }

    private LocalDate formatReleaseDate(String releaseDate) {
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate= LocalDate.parse(releaseDate,dateTimeFormatter);
        return localDate;
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

    @JsonProperty("release_date")
    @Override
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }
}
