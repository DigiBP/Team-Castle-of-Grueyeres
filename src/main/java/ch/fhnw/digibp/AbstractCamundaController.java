package ch.fhnw.digibp;

import java.util.Optional;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public abstract class AbstractCamundaController {

    protected final RuntimeService runtimeService;
    protected final TaskService taskService;
    protected final OrderRepository orderRepository;

    public AbstractCamundaController(OrderRepository orderRepository, RuntimeService runtimeService, TaskService taskService) {
        this.orderRepository = orderRepository;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    protected Task findTask(String uuid, String group, String taskKey) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskCandidateGroup(group);
        taskQuery.taskDefinitionKey(taskKey);
        taskQuery.processInstanceBusinessKey(uuid);
        return taskQuery.singleResult();
    }


    protected Order find(String orderUuid) {
        Optional<Order> order = orderRepository.findById(orderUuid);
        return order.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }
}
