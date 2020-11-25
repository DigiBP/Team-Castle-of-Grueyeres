package ch.fhnw.digibp.validation;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import ch.fhnw.digibp.recommendation.RecommendationEngine;
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
    private final RecommendationEngine recommendationEngine;

    @Autowired
    public ValidationDelegate(OrderRepository orderRepository, RecommendationEngine recommendationEngine) {
        this.orderRepository = orderRepository;
        this.recommendationEngine = recommendationEngine;
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
        Validation validation = recommendationEngine.recommend(order);
        order.setValidation(validation);
        orderRepository.save(order);
        LOGGER.info("Persisting order {}", order);
        execution.getVariables().clear();
        execution.setVariables(order.toMap());
    }

}
