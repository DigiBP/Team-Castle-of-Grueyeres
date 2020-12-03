package ch.fhnw.digibp.physician;

import java.util.List;
import java.util.stream.Collectors;

import ch.fhnw.digibp.AbstractCamundaController;
import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhysicianController extends AbstractCamundaController {

    @Autowired
    public PhysicianController(OrderRepository orderRepository, RuntimeService runtimeService, TaskService taskService) {
        super(orderRepository, runtimeService, taskService);
    }

    @GetMapping("/physician")
    public String physicianTasks(Model model) {
        List<Task> tasks = findTasks("Physician", "validate_analysis");
        Iterable<Order> orders = orderRepository.findAllSorted(tasks.stream().map(t -> (String) taskService.getVariables(t.getId()).get("uuid")).collect(Collectors.toList()));
        model.addAttribute("orders", orders);
        return "physician-overview";
    }

    protected List<Task> findTasks(String group, String taskKey) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskCandidateGroup(group);
        taskQuery.taskDefinitionKey(taskKey);
        return taskQuery.unlimitedList();
    }
}
