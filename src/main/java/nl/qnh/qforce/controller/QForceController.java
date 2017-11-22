package nl.qnh.qforce.controller;

import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.domain.PersonModel;
import nl.qnh.qforce.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

/**
 * Created by Javi on 21/11/2017.
 * This class will be the controller for managing the Rest Api requests
 */
@RestController
@RequestMapping(value = "/api")
public class QForceController {
    @Autowired
    PersonService personService;

    /**
     * Endpoint for retrieving persons by their name
     * @param characterName this is the parameter name
     * @return a list of persons
     */
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<Person> searchPersonsByName(@RequestParam(value = "q")String characterName) {
       List<Person> personList =  personService.search(characterName);

       return personList;
    }

    /**
     * Endpoint for retrieving persons by their id
     * @param id the person id for whom we want to search
     * @return the person details in case it exists
     */
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> searchPersonById(@PathVariable("id") long id) {
          Optional<Person> person = personService.get(id);
          return person.isPresent()?ResponseEntity.ok().body(person.get()):new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
