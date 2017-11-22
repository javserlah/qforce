package Mocking;

import nl.qnh.qforce.configuration.QForceProperties;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MockedQForceProperties {
    @Bean
    @Primary
    public QForceProperties getConsultClient() {
        return Mockito.mock(QForceProperties.class);
    }
}
