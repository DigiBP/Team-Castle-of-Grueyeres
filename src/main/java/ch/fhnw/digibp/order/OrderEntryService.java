package ch.fhnw.digibp.order;

import java.util.UUID;

import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderEntryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEntryService.class);

    private final RuntimeService runtimeService;

    @Autowired
    public OrderEntryService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping("/order")
    public void newOrder(@RequestBody Order order) {
        order.setUuid(UUID.randomUUID().toString());
        order.setState(Order.State.NEW);
        LOGGER.info("Received new order {}", order);
        runtimeService.startProcessInstanceByMessage("OrderEntryMessage", order.toMap());
    }


}
