package ch.fhnw.digibp.analysis;

import java.time.LocalDate;

import ch.fhnw.digibp.AbstractCamundaController;
import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import ch.fhnw.digibp.validation.Validation;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisService extends AbstractCamundaController {

    @Autowired
    public AnalysisService(OrderRepository orderRepository, RuntimeService runtimeService, TaskService taskService) {
        super(orderRepository, runtimeService, taskService);
    }

    public void save(String orderUuid, Analysis analysis) {
        Order persistedOrder = find(orderUuid);
        persistedOrder.getAnalysisResult().setRemarks(analysis.getRemarks());
        persistedOrder.getAnalysisResult().setResultValue(analysis.getResultValue());
        persistedOrder.getAnalysisResult().setResultDescription(analysis.getResultDescription());
        persistedOrder.getAnalysisResult().setEndDate(LocalDate.now());
        persistedOrder.setState(Order.State.ANALYSIS_DONE);
        orderRepository.save(persistedOrder);
        Task task = findTask(orderUuid, "Lab Technician", "submit_test_result");
        taskService.complete(task.getId(), persistedOrder.toMap());
    }

    public void confirm(String orderUuid, Validation validation) {
        Order persistedOrder = find(orderUuid);
        persistedOrder.getValidation().setApproved(true);
        persistedOrder.getValidation().setRecommendation(validation.getRecommendation());
        persistedOrder.getValidation().getAnalysisEntry().setRecommendation(validation.getRecommendation());
        Task task = findTask(orderUuid, "Physician", "validate_analysis");
        taskService.complete(task.getId(), persistedOrder.toMap());
    }

    public void reject(String orderUuid) {
        Order persistedOrder = find(orderUuid);
        persistedOrder.getValidation().setApproved(false);
        persistedOrder.setState(Order.State.IN_ANALYSIS);
        persistedOrder.setValidation(null);
        orderRepository.save(persistedOrder);
        Task task = findTask(orderUuid, "Physician", "validate_analysis");
        taskService.complete(task.getId(), persistedOrder.toMap());
    }
}
