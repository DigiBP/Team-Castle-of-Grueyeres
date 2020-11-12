package ch.fhnw.digibp.validation;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ValidationController {
    private final ProcessEngine processEngine;

    private final OrderRepository orderRepository;

    @Autowired
    public ValidationController(OrderRepository orderRepository, ProcessEngine processEngine) {
        this.orderRepository = orderRepository;
        this.processEngine = processEngine;
    }

    @GetMapping("/order/{uuid}/validation")
    public String order(Model model, @PathVariable(name = "uuid") String orderUuid) {

        return "submitTestResult";
    }

    @PostMapping(value = "/order/{orderUuid}/validation", params = "action=save")
    public String save_validation(@ModelAttribute Order order, @PathVariable(name = "orderUuid") String orderUuid, Model model) {
        return "redirect:/order/" + orderUuid;
    }
}
