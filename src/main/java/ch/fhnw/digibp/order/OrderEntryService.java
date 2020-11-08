package ch.fhnw.digibp.order;

import java.util.Optional;
import java.util.UUID;

import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpServerErrorException;

@Controller
public class OrderEntryService {

    private static final String UNEXPECTED_ERROR_MSG_TEMPLATE = "Unfortunately we were not able to process your request with id %s!";

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEntryService.class);

    private final RuntimeService runtimeService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderEntryService(RuntimeService runtimeService, OrderRepository orderRepository) {
        this.runtimeService = runtimeService;
        this.orderRepository = orderRepository;
    }

    public Order create(Order order) {
        final String uuid = UUID.randomUUID().toString();
        try {
            order.setUuid(uuid);
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

    public Order update(Order order, String uuid) {
        Order persistedOrder = load(uuid);
        persistedOrder.setComment(order.getComment());
        persistedOrder.setClientId(order.getClientId());
        persistedOrder.setDueDate(order.getDueDate());
        return orderRepository.save(persistedOrder);
    }

    public Order load(String uuid) {
        Optional<Order> order = orderRepository.findById(uuid);
        return order.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }

    private void throwHttpServerError(HttpStatus httpStatus, String template, String uuid) throws HttpServerErrorException {
        String msg = String.format(template, uuid);
        throw new HttpServerErrorException(httpStatus, msg);
    }

}
