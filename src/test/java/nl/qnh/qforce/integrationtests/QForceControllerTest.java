package nl.qnh.qforce.integrationtests;

import Mocking.MockResponses;
import Mocking.MockedQForceProperties;
import ResponseModel.PersonModelTest;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import nl.qnh.qforce.QlnhApplication;
import nl.qnh.qforce.configuration.QForceProperties;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.domain.PersonModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/***
 * Created by Javi on 21/11/2017.
 * Integration tests for testing the whole application but mocking (with WireMock) the third party Api in order to not depende on it for making the tests work
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QlnhApplication.class, MockedQForceProperties.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QForceControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    QForceProperties qForceProperties;

    @Rule
    public WireMockRule swapApiMock = new WireMockRule(wireMockConfig().port(8090));

    @Test
    public void searchingPersonsByExistentNameReturnsTheExpectedPersons() {
        String characterName = "r2";

        givenTheSwapApiIsAvailableAndReturningPersonsByName(characterName);

        givenTheSwapApiIsAvailableAndReturningMovies();

        ResponseEntity<PersonModelTest[]> qForceApiResponse = whenWeSearchPersonsByName(characterName);

        thenThePersonsReceivedAreReturnedAsExpected(qForceApiResponse.getBody());

        thenTheResponseStatusCodeIs(HttpStatus.OK, qForceApiResponse.getStatusCode());
    }

    @Test
    public void searchingAnExistentPersonByItsIdReturnsThePersonDetails() {
        int personResourceId = 1;

        givenTheSwapApiIsAvailableAndReturningAPersonWithId(personResourceId);

        givenTheSwapApiIsAvailableAndReturningMovies();

        ResponseEntity<PersonModelTest> qForceApiResponse = whenWeSearchForAPersonWithId(personResourceId);

        thenThePersonDetailsAreReceivedAsExpected(qForceApiResponse.getBody());

        thenTheResponseStatusCodeIs(HttpStatus.OK, qForceApiResponse.getStatusCode());
    }

    private void givenTheSwapApiIsAvailableAndReturningMovies() {
        when(qForceProperties.getSwapApiUrl()).thenReturn(String.format("http://localhost:%s", swapApiMock.port()));

        stubFor(get(urlPathEqualTo("/films/1"))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(MockResponses.Get("film"))));
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

    private ResponseEntity<PersonModelTest[]> whenWeSearchPersonsByName(String characterName) {
        RestTemplate restApi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.set("Accept", MediaType.APPLICATION_JSON.toString());

        URI qForceEndPoint = UriComponentsBuilder.fromUriString(String.format("http://localhost:%s", port))
                .path("/api/persons")
                .queryParam("q", characterName)
                .build()
                .toUri();

        return restApi.exchange(qForceEndPoint, HttpMethod.GET, new HttpEntity<PersonModelTest[]>(headers), PersonModelTest[].class);
    }

    private ResponseEntity<PersonModelTest> whenWeSearchForAPersonWithId(int personResourceId) {
        RestTemplate restApi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.set("Accept", MediaType.APPLICATION_JSON.toString());

        URI qForceEndPoint = UriComponentsBuilder.fromUriString(String.format("http://localhost:%s", port))
                .path(String.format("/api/persons/%s", personResourceId))
                .build()
                .toUri();

        return restApi.exchange(qForceEndPoint, HttpMethod.GET, new HttpEntity<PersonModelTest>(headers), PersonModelTest.class);

    }

    private void thenThePersonsReceivedAreReturnedAsExpected(PersonModelTest[] persons) {
        assertThat(persons[0].getName()).isEqualTo("Luke Skywalker");
        assertThat(persons[0].getMovies().size()).isGreaterThan(1);
    }

    private void thenTheResponseStatusCodeIs(HttpStatus httpStatus, HttpStatus qForceApiResponseStatus) {
        assertThat(qForceApiResponseStatus).isEqualTo(httpStatus);
    }

    private void thenThePersonDetailsAreReceivedAsExpected(PersonModelTest person) {
        assertThat(person.getName()).isEqualTo("C-3PO");
        assertThat(person.getMovies().size()).isGreaterThan(1);
    }

}
