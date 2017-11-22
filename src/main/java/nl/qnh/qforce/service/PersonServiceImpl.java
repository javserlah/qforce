package nl.qnh.qforce.service;

import nl.qnh.qforce.domain.Analytic;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.facade.SwApiFacade;
import nl.qnh.qforce.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        analyticsRepository.save(new Analytic(String.format("Trying to search a person by Name: %s",characterName), LocalDateTime.now()));

        return swapApiFacade.searchPersonsByName(characterName);
    }

    @Override
    public Optional<Person> get(long id) {
        analyticsRepository.save(new Analytic(String.format("Trying to get a person by Id: %s",id), LocalDateTime.now()));

        return swapApiFacade.searchPersonById(id);
    }
}
