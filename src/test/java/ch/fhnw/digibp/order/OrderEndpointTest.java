package ch.fhnw.digibp.order;

import java.util.Optional;

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
class OrderEndpointTest {

    private static final String URL_TEMPLATE = "http://localhost:%s/api/order";
    private static final String ORDER_UUID = "0a5fa6c2-329f-45ee-9925-13aed405dfde";
    private static final String REQUEST_BODY = "{\"clientId\":\"test\",\"analysis\":\"sars_cov_2\",\"comment\":\"please analyse this :)\"}";
    private static final String RESPONSE_BODY = "{\"uuid\":\"0a5fa6c2-329f-45ee-9925-13aed405dfde\",\"clientId\":\"test\",\"analysis\":\"sars_cov_2\",\"comment\":\"please analyse this :)\",\"dueDate\":null,\"priority\":null,\"state\":null,\"sample\":null,\"billingInformation\":{\"currency\":null,\"price\":0.0},\"sampleRequirements\":{\"temperature\":0.0,\"hazardCategory\":null,\"packageType\":null,\"sampleType\":null},\"biohazard\":false}";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OrderRepository orderRepository;

    @BeforeEach
    public void before() {
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0, Order.class));
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(buildTestOrder()));
    }

    @Test
    public void testOrderEntry() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(REQUEST_BODY, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(getUrl(), requestEntity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(RESPONSE_BODY, responseEntity.getBody());
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(orderRepository).findById(anyString());
    }

    @Test
    public void testOrderLookUp() {
        String url = getUrl() + "/" + ORDER_UUID;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(RESPONSE_BODY, responseEntity.getBody());
    }

    private Order buildTestOrder() {
        Order order = new Order();
        order.setAnalysis("sars_cov_2");
        order.setClientId("test");
        order.setComment("please analyse this :)");
        order.setUuid(ORDER_UUID);
        return order;
    }

    private String getUrl() {
        return String.format(URL_TEMPLATE, port);
    }
}