package ch.fhnw.digibp;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    private static final String URL_TEMPLATE = "http://localhost:%s/";

    @LocalServerPort
    private int port;

    protected String getUrl() {
        return String.format(URL_TEMPLATE, port);
    }
}
