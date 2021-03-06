package ch.fhnw.digibp.order;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import ch.fhnw.digibp.analysis.Analysis;
import ch.fhnw.digibp.client.Client;
import ch.fhnw.digibp.client.ClientRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpServerErrorException;

@Controller
public class OrderService {

    private static final String UNEXPECTED_ERROR_MSG_TEMPLATE = "Unfortunately we were not able to process your request with id %s!";

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final RuntimeService runtimeService;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public OrderService(RuntimeService runtimeService, OrderRepository orderRepository, ClientRepository clientRepository) {
        this.runtimeService = runtimeService;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    public Order create(Order order) {
        final String uuid = generateUuid();
        try {
            if (order.getClient() == null) {
                throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Client information missing");
            }
            order.setClient(find(order.getClient().getName()));
            order.setUuid(uuid);
            order.setOrderDate(LocalDate.now());
            order.setState(Order.State.NEW);
            LOGGER.info("Received new order {}", order);
            runtimeService.startProcessInstanceByMessage("OrderEntryMessage", uuid, order.toMap());
            Optional<Order> persistedOrder = orderRepository.findById(uuid);
            if (!persistedOrder.isPresent()) {
                LOGGER.error("Somehow the order {} was not persisted!", uuid);
                throwHttpServerError(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_MSG_TEMPLATE, uuid);
            }
            return persistedOrder.get();
        } catch (HttpServerErrorException e) {
            LOGGER.error("Expected error, returning error code {}.", e.getStatusCode(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error, returning an internal server error (500).", e);
            throwHttpServerError(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_MSG_TEMPLATE, uuid);
        }
        return null;
    }

    private String generateUuid() {
        return UUID.randomUUID().toString().substring(0, 8).replace("-", "");
    }

    public Order update(Order order, String uuid) {
        Order persistedOrder = load(uuid);
        persistedOrder.setComment(order.getComment());
        persistedOrder.setClient(order.getClient());
        persistedOrder.setPriority(order.getPriority());
        return orderRepository.save(persistedOrder);
    }

    public void startSampleEntry(Order order) {
        runtimeService.startProcessInstanceByKey("sample_entry", order.getUuid(), order.toMap());
    }

    public Order load(String uuid) {
        Optional<Order> order = orderRepository.findById(uuid);
        return order.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }

    public void startAnalysis(Order order) {
        Analysis analysis = new Analysis();
        analysis.setStartDate(LocalDate.now());
        order.setAnalysisResult(analysis);
        order.setState(Order.State.IN_ANALYSIS);
        orderRepository.save(order);
        runtimeService.startProcessInstanceByKey("analyse_sample", order.getUuid(), order.toMap());
    }

    private Client find(String name) {
        Optional<Client> client = clientRepository.findById(name);
        return client.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND, "Unknown client!"));
    }

    private void throwHttpServerError(HttpStatus httpStatus, String template, String uuid) throws HttpServerErrorException {
        String msg = String.format(template, uuid);
        throw new HttpServerErrorException(httpStatus, msg);
    }

}
