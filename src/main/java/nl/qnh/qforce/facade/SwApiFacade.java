package nl.qnh.qforce.facade;

import nl.qnh.qforce.configuration.QForceProperties;
import nl.qnh.qforce.domain.MovieModel;
import nl.qnh.qforce.domain.Paging;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.domain.PersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This class is a facade to call the third party api
 */
@Service
public class SwApiFacade {
    @Autowired
    QForceProperties qForceProperties;

    /***
     *
     * @param characterName the character name to search for
     * @return the list of persons related with the character name
     */
    public List<Person> searchPersonsByName(String characterName) {
        ResponseEntity<Paging> swapApiResponse = null;

        try {
            RestTemplate restApi = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_JSON.toString());
            headers.set("Accept", MediaType.APPLICATION_JSON.toString());
            headers.set("User-Agent", "testswap");

            URI swapResource = UriComponentsBuilder.fromUriString(qForceProperties.getSwapApiUrl())
                    .path("/people/")
                    .queryParam("search", characterName)
                    .build()
                    .toUri();
            swapApiResponse = restApi.exchange(swapResource, HttpMethod.GET, new HttpEntity<Paging>(headers), Paging.class);
            if (swapApiResponse.getStatusCode() == HttpStatus.OK) {
                return Arrays.asList(swapApiResponse.getBody().getResults());
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param id the id of the person to search for
     * @return the person details
     */
    public Optional<Person> searchPersonById(long id) {
        ResponseEntity<PersonModel> swapApiResponse = null;
        try {
            RestTemplate restApi = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_JSON.toString());
            headers.set("Accept", MediaType.APPLICATION_JSON.toString());
            headers.set("User-Agent", "testswap");

            URI swapResource = UriComponentsBuilder.fromUriString(qForceProperties.getSwapApiUrl())
                    .path(String.format("/people/%s",id))
                    .build()
                    .toUri();
            swapApiResponse = restApi.exchange(swapResource, HttpMethod.GET, new HttpEntity<PersonModel>(headers), PersonModel.class);
            if (swapApiResponse.getStatusCode() == HttpStatus.OK) {
                return Optional.of(swapApiResponse.getBody());
            } else {
                return Optional.ofNullable(null);
            }
        } catch (Exception ex) {
            return Optional.ofNullable(null);
        }
    }
}
