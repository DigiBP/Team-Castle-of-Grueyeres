package ch.fhnw.digibp.sample;

import java.time.ZonedDateTime;

import ch.fhnw.digibp.AbstractCamundaController;
import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SampleController extends AbstractCamundaController {

    @Autowired
    public SampleController(OrderRepository orderRepository, RuntimeService runtimeService, TaskService taskService) {
        super(orderRepository, runtimeService, taskService);
    }

    @GetMapping("/order/{order}/sample")
    public String sample(Model model, @PathVariable(name = "order") String orderUuid) {
        Order order = find(orderUuid);
        if (order.getSample() == null) {
            order.setSample(new Sample());
        }
        model.addAttribute("sample", order.getSample());
        model.addAttribute("uuid", orderUuid);
        return "sample-entry";
    }

    @PostMapping("/order/{order}/sample")
    public String sample_submit(@ModelAttribute Sample sample, @PathVariable(name = "order") String orderUuid, Model model) {
        Order order = find(orderUuid);
        order.setSample(sample);
        order.getSample().setEntryDate(ZonedDateTime.now());
        order.getSample().setUpdateDate(ZonedDateTime.now());
        order.setState(Order.State.SAMPLE_RECEIVED);
        orderRepository.save(order);
        Task task = findTask(orderUuid, "Lab Technician", "enter_sample");
        taskService.complete(task.getId(), order.toMap());
        model.addAttribute("sample", order.getSample());
        model.addAttribute("uuid", orderUuid);
        return "redirect:/order/" + orderUuid;
    }
}
