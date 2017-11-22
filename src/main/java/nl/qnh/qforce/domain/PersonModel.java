package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Javi on 21/11/2017.
 * Implements the person interface for retrieving person details
 */
public class PersonModel implements Person {
    long id;

    String name;

    /**
     * It maps the third party json field syntaxis into our own syntaxis
     */
    @JsonProperty("birth_year")
    String birthYear;

    Gender gender;

    Integer height;

    Integer weight;

    List<Movie> movies;

    public PersonModel() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

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
}
