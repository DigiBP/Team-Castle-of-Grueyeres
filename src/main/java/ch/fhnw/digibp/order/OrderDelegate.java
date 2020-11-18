package ch.fhnw.digibp.order;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDelegate implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDelegate.class);

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDelegate(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(DelegateExecution execution) {
        switch (execution.getCurrentActivityId()) {
            case "approve":
            case "store_order":
                storeOrder(execution);
                break;
            default:
                LOGGER.error("Unexpected activity '{}'", execution.getCurrentActivityId());
        }
    }

    private void storeOrder(DelegateExecution execution) {
        Order order = new Order(execution.getVariables());
        order = orderRepository.save(order);
        execution.getVariables().clear();
        execution.setVariables(order.toMap());
        LOGGER.info("Persisting order {}", order);
    }
}
