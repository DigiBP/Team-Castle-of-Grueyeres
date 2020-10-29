package ch.fhnw.digibp.order;

import ch.fhnw.digibp.sample.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
    private final OrderEntryService orderEntryService;

    @Autowired
    public OrderController(OrderEntryService orderEntryService) {
        this.orderEntryService = orderEntryService;
    }

    @GetMapping("/order")
    public String sample(Model model) {
        model.addAttribute("order", new Order());
        return "order-entry";
    }

    @GetMapping("/order/{order}")
    public String sample(Model model, @PathVariable(name = "order") String orderUuid) {
        Order order = orderEntryService.load(orderUuid);
        model.addAttribute("order", order);
        model.addAttribute("uuid", orderUuid);
        return "order-entry";
    }

    @PostMapping("/order/")
    public String sample_submit(@ModelAttribute Order order, @ModelAttribute Sample sample, Model model) {
        order.setSample(sample);
        Order actualOrder = orderEntryService.create(order);
        model.addAttribute("order", actualOrder);
        return "redirect:" + order.getUuid();
    }

    @PostMapping("/order/{uuid}")
    public String sample_submit(@ModelAttribute Order order, @ModelAttribute Sample sample, @PathVariable(name = "order") String orderUuid, Model model) {
        order.setSample(sample);
        Order actualOrder = orderEntryService.update(order, orderUuid);
        model.addAttribute("order", actualOrder);
        return "redirect:" + order.getUuid();
    }

}
