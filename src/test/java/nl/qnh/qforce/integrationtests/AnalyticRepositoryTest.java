package nl.qnh.qforce.integrationtests;

import com.flextrade.jfixture.JFixture;
import com.google.common.collect.Lists;
import nl.qnh.qforce.QlnhApplication;
import nl.qnh.qforce.domain.Analytic;
import nl.qnh.qforce.repository.AnalyticsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/***
 * Created by Javi on 21/11/2017.
 * Repository tests for making sure that the DataBase is storing the information correctly
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QlnhApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnalyticRepositoryTest {
    @Autowired
    AnalyticsRepository analyticsRepository;

    JFixture jFixture = new JFixture();

    /***
     * We want a clear DataBase before running the tests
     */
    @Before
    public void deleteAll()
    {
        analyticsRepository.deleteAll();
    }

    @Test
    public void analyticsAreSavedAsExpected() {
        int totalNumberOfAnalytics = 3;
        givenWeSaveATotalNumberOfAnalyticsOf(totalNumberOfAnalytics);
        List<Analytic> analyticList = whenWeRetrieveAllTheAnalyticsFromTheDataBase();
        thenTheTotalNumberOfAnalyticsIsExpectedToBe(3,analyticList );
    }

    private void givenWeSaveATotalNumberOfAnalyticsOf(int totalNumberOfAnalytics) {
        jFixture.customise().repeatCount(totalNumberOfAnalytics);
        Analytic[] analyticsList = jFixture.create(Analytic[].class);
        for (Analytic analytic : analyticsList) {
            analyticsRepository.save(analytic);
        }
    }

    private List<Analytic> whenWeRetrieveAllTheAnalyticsFromTheDataBase() {
       return Lists.newArrayList(analyticsRepository.findAll());
    }

    private void thenTheTotalNumberOfAnalyticsIsExpectedToBe(int expectedNumberOfAnalytics, List<Analytic> analyticList) {
        assertThat(analyticList).hasSize(expectedNumberOfAnalytics);
    }




}
