package ch.fhnw.digibp.order;

import java.util.Optional;

import ch.fhnw.digibp.AbstractIntegrationTest;
import ch.fhnw.digibp.TestDataFactory;
import ch.fhnw.digibp.client.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderEndpointTest extends AbstractIntegrationTest {

    private static final String ENDPOINT = "api/order";
    private static final String REQUEST_BODY = "{\"client\":{\"name\":\"123\"},\"analysisType\":\"Sars_Cov_2\",\"comment\":\"please analyse this :)\"}";
    private static final String RESPONSE_BODY = "{\"uuid\":\"0a5fa6c2-329f-45ee-9925-13aed405dfde\",\"analysisType\":\"Sars_Cov_2\",\"comment\":\"please analyse this :)\",\"laboratory\":null,\"orderDate\":null,\"priority\":\"LOW\",\"state\":\"NEW\",\"sample\":null,\"billingInformation\":{\"currency\":\"CHF\",\"price\":106.0},\"sampleRequirements\":{\"temperature\":0.0,\"severityOfMisClassification\":\"MEDIUM\",\"sampleType\":\"SALIVA\",\"biohazard\":true},\"analysisResult\":null,\"validation\":null,\"client\":{\"name\":\"123\",\"email\":\"123@123.ch\"},\"sampleTypeMismatch\":false}";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ClientRepository clientRepository;

    @BeforeEach
    public void before() {
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0, Order.class));
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(TestDataFactory.buildTestOrder()));
        when(clientRepository.findById(anyString())).thenReturn(Optional.of(TestDataFactory.buildClient()));
    }

    @Test
    public void testOrderEntry() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(REQUEST_BODY, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(getUrl(), requestEntity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(RESPONSE_BODY, responseEntity.getBody());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderRepository).findById(anyString());
        verify(clientRepository).findById(anyString());
    }

    @Test
    public void testOrderLookUp() {
        String url = getUrl() + "/" + TestDataFactory.ORDER_UUID;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(RESPONSE_BODY, responseEntity.getBody());
    }


    @Override
    protected String getUrl() {
        return super.getUrl() + ENDPOINT;
    }
}