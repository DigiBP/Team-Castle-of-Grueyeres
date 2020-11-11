package ch.fhnw.digibp.validation;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationDelegate implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationDelegate.class);

    private final OrderRepository orderRepository;

    @Autowired
    public ValidationDelegate(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        switch (execution.getCurrentActivityId()) {
            case "ml_recommendation":
                ml_recommendation(execution);
                break;
            default:
                LOGGER.error("Unexpected activity '{}'", execution.getCurrentActivityId());
        }
    }

    private void ml_recommendation(DelegateExecution execution) {
        Order order = new Order(execution.getVariables());
        Validation validation = new Validation();
        validation.setProbability(40);
        validation.setMlRecommendation("Some recommendation");
        order.setValidation(validation);
        orderRepository.save(order);
        LOGGER.info("Persisting order {}", order);
    }
}
