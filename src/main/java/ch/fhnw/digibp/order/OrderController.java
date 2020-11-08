package ch.fhnw.digibp.order;

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
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderEntryService orderEntryService, OrderRepository orderRepository) {
        this.orderEntryService = orderEntryService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("order", new Order());
        return "order-entry";
    }

    @GetMapping("/orders")
    public String ordersOverview(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "order-overview";
    }

    @GetMapping("/order/{order}")
    public String order(Model model, @PathVariable(name = "order") String orderUuid) {
        Order order = orderEntryService.load(orderUuid);
        model.addAttribute("order", order);
        model.addAttribute("uuid", orderUuid);
        return "order-entry";
    }

    @PostMapping("/order/")
    public String order_submit(@ModelAttribute Order order, Model model) {
        Order actualOrder = orderEntryService.create(order);
        model.addAttribute("order", actualOrder);
        return "redirect:" + order.getUuid();
    }

    @PostMapping(value = "/order/{order}", params = "action=submit")
    public String order_submit(@ModelAttribute Order order, @PathVariable(name = "order") String orderUuid, Model model) {
        Order actualOrder = orderEntryService.update(order, orderUuid);
        model.addAttribute("order", actualOrder);
        return "redirect:" + order.getUuid();
    }

}
