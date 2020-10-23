package ch.fhnw.digibp.order;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderDelegate implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDelegate.class);

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
        LOGGER.info("Persisting order {}", order);
    }

    private void cancelOrder(DelegateExecution execution) {
        LOGGER.info("Cancelling order {}", new Order(execution.getVariables()));
    }

    private void confirmOrder(DelegateExecution execution) {
        LOGGER.info("Confirming order {}", new Order(execution.getVariables()));
    }
}
