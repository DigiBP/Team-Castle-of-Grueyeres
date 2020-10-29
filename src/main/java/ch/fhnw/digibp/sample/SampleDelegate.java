package ch.fhnw.digibp.sample;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleDelegate implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleDelegate.class);

    private final OrderRepository orderRepository;

    @Autowired
    public SampleDelegate(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(DelegateExecution execution) {
        switch (execution.getCurrentActivityId()) {
            case "sample_cancel_notification":
                cancelOrder(execution);
                break;
            case "sample_entry_finished":
                storeOrder(execution);
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
}