package ch.fhnw.digibp.order;

import ch.fhnw.digibp.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public OrderController(OrderService orderService, OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("clients", clientRepository.findAll());
        return "order-entry";
    }

    @GetMapping("/orders")
    public String ordersOverview(Model model) {
        model.addAttribute("orders", orderRepository.findAllSorted());
        return "order-overview";
    }

    @GetMapping("/order/{order}")
    public String order(Model model, @PathVariable(name = "order") String orderUuid) {
        Order order = orderService.load(orderUuid);
        model.addAttribute("order", order);
        model.addAttribute("uuid", orderUuid);
        model.addAttribute("clients", clientRepository.findAll());
        return "order-entry";
    }

    @PostMapping("/order/")
    public String order_submit(@ModelAttribute Order order, Model model) {
        Order actualOrder = orderService.create(order);
        model.addAttribute("order", actualOrder);
        return "redirect:/orders";
    }

    @PostMapping(value = "/order/{order}", params = "action=submit")
    public String order_submit(@ModelAttribute Order order, @PathVariable(name = "order") String orderUuid, Model model) {
        Order actualOrder = orderService.update(order, orderUuid);
        model.addAttribute("order", actualOrder);
        return "redirect:/orders";
    }

    @PostMapping(value = "/order/{order}", params = "action=enterSample")
    public String enter_sample(@ModelAttribute Order order, @PathVariable(name = "order") String orderUuid, Model model) {
        orderService.startSampleEntry(order);
        return "redirect:" + order.getUuid() + "/sample";
    }

    @PostMapping(value = "/order/{order}", params = "action=startAnalysis")
    public String start_order_analysis(@ModelAttribute Order order, @PathVariable(name = "order") String orderUuid) {
        orderService.startAnalysis(order);
        return "redirect:" + orderUuid + "/analysis";
    }

    @PostMapping(value = "/order/{order}", params = "action=enterAnalysisResult")
    public String enter_order_analysis(@ModelAttribute Order order, @PathVariable(name = "order") String orderUuid, Model model) {
        return "redirect:" + orderUuid + "/analysis";
    }
}
