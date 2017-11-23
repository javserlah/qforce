package nl.qnh.qforce.unittests;


import com.flextrade.jfixture.JFixture;
import nl.qnh.qforce.controller.QForceController;
import nl.qnh.qforce.domain.Movie;
import nl.qnh.qforce.domain.MovieModel;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.domain.PersonModel;
import Mocking.MockResponses;
import nl.qnh.qforce.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

/***
 * Unit tests for testing the QForce Controller class individually
 */
public class QForceControllerTest {
    @Mock
    private PersonService personService;

    @InjectMocks
    private QForceController qForceController;

    private JFixture jFixture = new JFixture();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        jFixture.customise().useSubType(Movie.class, MovieModel.class);
    }


    @Test
    public void searchingPersonsByExistentNameReturnsTheExpectedPersons() {
        MockResponses.Get("person");


        int totalNumberOfExistentPersonsByTheGivenName = 3;

        givenThereAreATotalNumberOfPersonsOf(totalNumberOfExistentPersonsByTheGivenName);

        List<Person> personsReturned = whenWeSearchForCharactersByTheName("characterNameTest");

        thenTheTotalNumberOfPersonsReturnedIsOf(totalNumberOfExistentPersonsByTheGivenName, personsReturned);
    }

    @Test
    public void searchingNonExistentPersonByIdReturnsNotFoundStatus() {
        int nonExistentPersonId = 1;
        givenThereIsNoPersonWithAnIdOf(nonExistentPersonId);
        ResponseEntity<Person> qForceControllerResponse = whenWeRequestAPersonByTheId(nonExistentPersonId);
        thenTheResponseStatusCodeIsExpectedToBe(HttpStatus.NOT_FOUND, qForceControllerResponse);
    }

    @Test
    public void searchingAnExistentPersonByIdReturnsAcceptedStatus() {
        int existentPersonId = 1;
        givenThereIsAPersonWithAnIdOf(existentPersonId);
        ResponseEntity<Person> qForceControllerResponse = whenWeRequestAPersonByTheId(existentPersonId);
        thenTheResponseStatusCodeIsExpectedToBe(HttpStatus.OK, qForceControllerResponse);
    }

    private void givenThereIsAPersonWithAnIdOf(int existentPersonId) {
        Optional<Person> person = Optional.of(jFixture.create(PersonModel.class));
        doReturn(person).when(personService).get(existentPersonId);
    }

    private void givenThereIsNoPersonWithAnIdOf(int nonExitentPersonId) {
        Optional<Person> person = Optional.ofNullable(null);
        doReturn(person).when(personService).get(nonExitentPersonId);
    }

    private void givenThereAreATotalNumberOfPersonsOf(int expectedNumberOfPersonsReturned) {
        jFixture.customise().repeatCount(expectedNumberOfPersonsReturned);
        doReturn(Arrays.asList(jFixture.create(PersonModel[].class))).when(personService).search(any());
    }

    private List<Person> whenWeSearchForCharactersByTheName(String characterName) {
        return qForceController.searchPersonsByName(characterName).getBody();
    }

    private ResponseEntity<Person> whenWeRequestAPersonByTheId(int personId) {
        return qForceController.searchPersonById(personId);
    }

    private void thenTheTotalNumberOfPersonsReturnedIsOf(int totalNumberOfExpectedPersons, List<Person> personsReturned) {
        assertThat(personsReturned).hasSize(totalNumberOfExpectedPersons);
    }

    private void thenTheResponseStatusCodeIsExpectedToBe(HttpStatus httpStatus, ResponseEntity<Person> qForceControllerResponse) {
        assertThat(qForceControllerResponse.getStatusCode()).isEqualTo(httpStatus);
    }
}
