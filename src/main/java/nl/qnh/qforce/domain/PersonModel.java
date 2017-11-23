package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.qnh.qforce.facade.SwApiFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Javi on 21/11/2017.
 * Implements the person interface for retrieving person details
 */
@JsonPropertyOrder({ "id", "name","birthYear", "gender","height","weight","films","movies"})
public class PersonModel implements Person {
    private long id;

    private String name;

    private String birthYear;

    private Gender gender;

    private Integer height;

    private Integer weight;

    private String[] films;

    private List<Movie> movies;

    public PersonModel() {
    }

    @JsonCreator
    public PersonModel(@JsonProperty("url") String url, @JsonProperty("name") String name, @JsonProperty("birth_year") String birthYear, @JsonProperty("gender") String gender, @JsonProperty("height") Integer height, @JsonProperty("weight") Integer weight, @JsonProperty("films") String[] films) {
        this.id = extractIdFromUrl(url);
        this.name = name;
        this.birthYear = birthYear;
        this.gender = castGenderFrom(gender);
        this.height = height;
        this.weight = weight;
        this.films = films;
    }

    private Gender castGenderFrom(String gender) {
        switch (gender.toUpperCase())
        {
            case "MALE":
                return Gender.MALE;
            case "FEMALE":
                return Gender.FEMALE;
            case "N/A":
                return Gender.NOT_APPLICABLE;
            default:
                return Gender.UNKNOWN;

        }
    }

    private int extractIdFromUrl(@JsonProperty("url") String url) {
        return Integer.parseInt(url.replaceAll("\\D+", ""));
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @JsonProperty("birth_year")
    @Override
    public String getBirthYear() {
        return this.birthYear;
    }

    @Override
    public Gender getGender() {
        return this.gender;
    }

    @Override
    public Integer getHeight() {
        return this.height;
    }

    @Override
    public Integer getWeight() {
        return this.weight;
    }

    @Override
    public List<Movie> getMovies() {
        return this.movies;
    }

    @JsonIgnore
    public String[] getFilms() {
        return films;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
