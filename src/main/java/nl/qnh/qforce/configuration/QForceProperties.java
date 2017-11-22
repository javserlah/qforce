package nl.qnh.qforce.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * Created by Javi on 21/11/2017.
 * This class will load the application.properties file into a class to make it easier to work with them
 */
@Component
public class QForceProperties {
    /**
     * The port where the sever will run
     */
    @Value("${server.port}")
    @Min(1)
    private int serverPort;

    /**
     * The swapapi url
     */
    @Value("${swapapi.url}")
    @NotNull
    private String swapApiUrl;

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getSwapApiUrl() {
        return swapApiUrl;
    }

    public void setSwapApiUrl(String swapApiUrl) {
        this.swapApiUrl = swapApiUrl;
    }
}
