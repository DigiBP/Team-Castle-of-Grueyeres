package ch.fhnw.digibp.order;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderEntryService {

    private final RuntimeService runtimeService;

    @Autowired
    public OrderEntryService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping("/order")
    public void newOrder(@RequestBody Order order) {
        Map<String, Object> map = new HashMap<>();
        map.put("clientId", order.getClientId());
        map.put("analysis", order.getAnalysis());
        map.put("dueDate", order.getDueDate());
        runtimeService.startProcessInstanceByMessage("OrderEntryMessage", map);
    }
}
