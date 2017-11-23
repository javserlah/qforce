package nl.qnh.qforce.unittests;

import Mocking.MockResponses;
import com.flextrade.jfixture.JFixture;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import nl.qnh.qforce.configuration.QForceProperties;
import nl.qnh.qforce.controller.QForceController;
import nl.qnh.qforce.domain.Movie;
import nl.qnh.qforce.domain.MovieModel;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.domain.PersonModel;
import nl.qnh.qforce.facade.SwApiFacade;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/***
 * The unit tests for testing the facade component
 */
public class SwApiFacadeTest {
    @InjectMocks
    private SwApiFacade swApiFacade;

    @Mock
    private QForceProperties qForceProperties;

    @Rule
    public WireMockRule swapApiMock = new WireMockRule(wireMockConfig().port(8090));

    private JFixture jFixture = new JFixture();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenThereAreDifferentPersonsAssociatedWithANameThenThePersonsAreReceivedAsExpected() {
        String characterName = jFixture.create(String.class);
        givenTheSwapApiIsAvailableAndReturningPersonsByName(characterName);
        List<PersonModel> personList = whenThePersonsAreRequestedByName(characterName);
        thenThePersonsAreReturnedAsExpected(personList);
    }

    @Test
    public void givenAPersonExistsWhenThePersonDetailsAreRequestedThenTheDetailsAreReceivedAsExpected() {
        int personId = jFixture.create(Integer.class);
        givenTheSwapApiIsAvailableAndReturningAPersonWithId(personId);
        Optional<PersonModel> person = whenAPersonIsRequestedById(personId);
        thenThePersonDetailsAreReturnedAsExpected(person);
    }

    @Test
    public void whenTheSwApiIsFailingANullPersonIsReturned() {
        int personId = jFixture.create(Integer.class);
        givenTheSwApiEndPointIsBroken();
        Optional<PersonModel> person = whenAPersonIsRequestedById(personId);
        thenNoPersonDetailsAreReturned(person);
    }

    private void givenTheSwapApiIsAvailableAndReturningAPersonWithId(int personResourceId) {
        when(qForceProperties.getSwapApiUrl()).thenReturn(String.format("http://localhost:%s", swapApiMock.port()));

        stubFor(get(urlPathEqualTo(String.format("/people/%s", personResourceId)))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(MockResponses.Get("person"))));
    }

    private void givenTheSwapApiIsAvailableAndReturningPersonsByName(String characterName) {
        when(qForceProperties.getSwapApiUrl()).thenReturn(String.format("http://localhost:%s", swapApiMock.port()));

        stubFor(get(urlPathEqualTo("/people/"))
                .withQueryParam("search", matching(characterName))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(MockResponses.Get("persons"))));
    }

    private List<PersonModel> whenThePersonsAreRequestedByName(String characterName) {
        return swApiFacade.searchPersonsByName(characterName);
    }

    private void givenTheSwApiEndPointIsBroken() {
        when(qForceProperties.getSwapApiUrl()).thenReturn("http://localhost:4545");
    }


    private Optional<PersonModel> whenAPersonIsRequestedById(int personId) {
       return swApiFacade.searchPersonById(personId);
    }

    private void thenNoPersonDetailsAreReturned(Optional<PersonModel> person) {
        assertThat(person.isPresent()).isFalse();
    }

    private void thenThePersonDetailsAreReturnedAsExpected(Optional<PersonModel> person) {
                assertThat(person.isPresent()).isTrue();
                assertThat(person.get().getName()).isEqualTo("C-3PO");
    }

    private void thenThePersonsAreReturnedAsExpected(List<PersonModel> personList) {
        assertThat(personList).hasSize(3);
        assertThat(personList.get(0).getName()).isEqualTo("Luke Skywalker");
    }
}
