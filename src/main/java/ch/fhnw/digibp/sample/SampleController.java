package ch.fhnw.digibp.sample;

import java.time.ZonedDateTime;
import java.util.Optional;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
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

    private final OrderRepository orderRepository;

    @Autowired
    public SampleController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/sample/{order}")
    public String sample(Model model, @PathVariable(name = "order") String orderUuid) {
        Order order = find(orderUuid);
        if (order.getSample() == null) {
            order.setSample(new Sample());
        }
        model.addAttribute("sample", order.getSample());
        model.addAttribute("uuid", orderUuid);
        return "sample-entry";
    }

    @PostMapping("/sample/{order}")
    public String sample_submit(@ModelAttribute Sample sample, @PathVariable(name = "order") String orderUuid, Model model) {
        Order order = find(orderUuid);
        order.setSample(sample);
        order.getSample().setEntryDate(ZonedDateTime.now());
        order.getSample().setUpdateDate(ZonedDateTime.now());
        order.setState(Order.State.SAMPLE_RECEIVED);
        orderRepository.save(order);
        model.addAttribute("sample", order.getSample());
        model.addAttribute("uuid", orderUuid);
        return "redirect:" + orderUuid;
    }

    private Order find(String orderUuid) {
        Optional<Order> order = orderRepository.findById(orderUuid);
        return order.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }
}
