package ch.fhnw.digibp.order;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderDelegate implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDelegate.class);

    private final OrderRepository orderRepository;

    public OrderDelegate(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(DelegateExecution execution) {
        switch (execution.getCurrentActivityId()) {
            case "store_order":
                storeOrder(execution);
                break;
            case "cancel_order":
                cancelOrder(execution);
                break;
            case "confirm_order":
                confirmOrder(execution);
                break;
            default:
                LOGGER.error("Unexpected activity '{}'", execution.getCurrentActivityId());
        }
    }

    private void storeOrder(DelegateExecution execution) {
        Order order = new Order(execution.getVariables());
        orderRepository.save(order);
        LOGGER.info("Persisting order {}", order);
    }

    private void cancelOrder(DelegateExecution execution) {
        Order order = new Order(execution.getVariables());
        orderRepository.save(order);
        LOGGER.info("Cancelling order {}", order);
    }

    private void confirmOrder(DelegateExecution execution) {
        Order order = new Order(execution.getVariables());
        orderRepository.save(order);
        LOGGER.info("Confirming order {}", order);
    }
}
