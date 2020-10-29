package ch.fhnw.digibp.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderEndpoint {

    private final OrderEntryService orderEntryService;

    @Autowired
    public OrderEndpoint(OrderEntryService orderEntryService) {
        this.orderEntryService = orderEntryService;
    }

    @PostMapping("/api/order")
    public @ResponseBody
    Order post(@RequestBody Order order) {
        return orderEntryService.create(order);
    }

    @GetMapping("/api/order/{uuid}")
    public @ResponseBody
    Order get(@PathVariable("uuid") String uuid) {
        return orderEntryService.load(uuid);
    }
    
}
