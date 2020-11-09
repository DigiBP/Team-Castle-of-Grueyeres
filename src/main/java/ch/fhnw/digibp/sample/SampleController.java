package ch.fhnw.digibp.sample;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpServerErrorException;

@Controller
public class SampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    private final RuntimeService runtimeService;

    private final OrderRepository orderRepository;

    @Autowired
    public SampleController(OrderRepository orderRepository, RuntimeService runtimeService) {
        this.orderRepository = orderRepository;
        this.runtimeService = runtimeService;
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
        model.addAttribute("sample", order.getSample());
        model.addAttribute("uuid", orderUuid);

        runtimeService.startProcessInstanceByKey("sample_entry", order.getUuid(), loadProcessContext(order));
        return "redirect:/order/" + orderUuid;
    }

    /**
     * This is a workaround, since I didn't find any better solution for now
     */
    public Map<String, Object> loadProcessContext(Order order) {
        Map<String, Object> processContext = order.toMap();
        processContext.putAll(order.getSample().toMapWithPrefix());
        processContext.putAll(order.getSampleRequirements().toMapWithPrefix());
        return processContext;
    }

    private Order find(String orderUuid) {
        Optional<Order> order = orderRepository.findById(orderUuid);
        return order.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }
}
