package ch.fhnw.digibp.analysis;

import java.time.LocalDate;

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
public class AnalysisController extends AbstractCamundaController {


    @Autowired
    public AnalysisController(OrderRepository orderRepository, RuntimeService runtimeService, TaskService taskService) {
        super(orderRepository, runtimeService, taskService);
    }

    @GetMapping("/order/{uuid}/analysis")
    public String order(Model model, @PathVariable(name = "uuid") String orderUuid) {
        Order order = find(orderUuid);
        if (order.getAnalysisResult() == null) {
            order.setAnalysisResult(new Analysis());
        }
        model.addAttribute("order", order);
        return "submitTestResult";
    }

    @PostMapping(value = "/order/{orderUuid}/analysis", params = "action=save")
    public String save_analysis(@ModelAttribute Order order, @PathVariable(name = "orderUuid") String orderUuid, Model model) {
        Order persistedOrder = find(orderUuid);
        Analysis analysis = order.getAnalysisResult();
        persistedOrder.getAnalysisResult().setMethodDescription(analysis.getMethodDescription());
        persistedOrder.getAnalysisResult().setRemarks(analysis.getRemarks());
        persistedOrder.getAnalysisResult().setResultCategory(analysis.getResultCategory());
        persistedOrder.getAnalysisResult().setResultDescription(analysis.getResultDescription());
        persistedOrder.getAnalysisResult().setEndDate(LocalDate.now());
        persistedOrder.setState(Order.State.ANALYSIS_DONE);
        orderRepository.save(persistedOrder);
        Task task = findTask(orderUuid, "Lab Technician", "submit_test_result");
        taskService.complete(task.getId(), persistedOrder.toMap());
        return "redirect:/order/" + orderUuid;
    }

    @PostMapping(value = "/order/{orderUuid}/analysis", params = "action=confirm")
    public String confirm_analysis(@ModelAttribute Order order, @PathVariable(name = "orderUuid") String orderUuid, Model model) {
        Order persistedOrder = find(orderUuid);
        persistedOrder.getValidation().setApproved(true);
        Task task = findTask(orderUuid, "Physician", "validate_analysis");
        taskService.complete(task.getId(), persistedOrder.toMap());
        return "redirect:/order/" + orderUuid;
    }

    @PostMapping(value = "/order/{orderUuid}/analysis", params = "action=reject")
    public String reject_analysis(@ModelAttribute Order order, @PathVariable(name = "orderUuid") String orderUuid, Model model) {
        Analysis analysis = new Analysis();
        analysis.setStartDate(LocalDate.now());
        order.setAnalysisResult(analysis);
        order.setState(Order.State.IN_ANALYSIS);
        orderRepository.save(order);
        Task task = findTask(orderUuid, "Physician", "validate_analysis");
        taskService.complete(task.getId(), order.toMap());
        return "redirect:/order/" + orderUuid;
    }


}
