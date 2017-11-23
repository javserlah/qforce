package nl.qnh.qforce.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.qnh.qforce.domain.Analytic;
import nl.qnh.qforce.domain.Movie;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.domain.PersonModel;
import nl.qnh.qforce.facade.SwApiFacade;
import nl.qnh.qforce.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Javi on 21/11/2017.
 * Persoce service layer for the application logic
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    SwApiFacade swapApiFacade;

    @Autowired
    AnalyticsRepository analyticsRepository;

    @Override
    public List<Person> search(String characterName) {
        analyticsRepository.save(new Analytic(String.format("Trying to search a person by Name: %s", characterName), LocalDateTime.now()));

        List<Person> finalPersonList = new ArrayList<>();

        List<PersonModel> personList = swapApiFacade.searchPersonsByName(characterName);

        for (PersonModel personModel : personList) {
            personModel.setMovies(getMoviesFrom(personModel.getFilms()));
            finalPersonList.add(personModel);
        }

        return finalPersonList;
    }

    @Override
    public Optional<Person> get(long id) {
        Optional<Person> person;

        analyticsRepository.save(new Analytic(String.format("Trying to get a person by Id: %s", id), LocalDateTime.now()));

        PersonModel personModel = swapApiFacade.searchPersonById(id).get();

        personModel.setMovies(getMoviesFrom(personModel.getFilms()));

        return Optional.of(personModel);
    }

    private List<Movie> getMoviesFrom(String[] films) {
        List<Movie> movieList = new ArrayList<>();
        for (String film : films) {
            movieList.add(swapApiFacade.searchFilmById(extractIdFromUrl(film)));
        }

        return movieList;
    }

    private int extractIdFromUrl(String url) {
        return Integer.parseInt(url.replaceAll("\\D+", ""));
    }
}
