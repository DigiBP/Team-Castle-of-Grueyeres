package ch.fhnw.digibp.analysis;

import java.time.LocalDate;
import java.util.Optional;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.RuntimeService;
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
public class AnalysisController {

    private final RuntimeService runtimeService;

    private final OrderRepository orderRepository;

    @Autowired
    public AnalysisController(OrderRepository orderRepository, RuntimeService runtimeService) {
        this.orderRepository = orderRepository;
        this.runtimeService = runtimeService;
    }

    @GetMapping("/order/{uuid}/analysis")
    public String order(Model model, @PathVariable(name = "uuid") String orderUuid) {
        Order order = find(orderUuid);
        if (order.getAnalysisResult() == null) {
            order.setAnalysisResult(new Analysis());
        }
        model.addAttribute("order", order);
        model.addAttribute("analysis", order.getAnalysisResult());
        return "submitTestResult";
    }

    @PostMapping(value = "/order/{orderUuid}/analysis", params = "action=save")
    public String save_analysis(@ModelAttribute Analysis analysis, @PathVariable(name = "orderUuid") String orderUuid, Model model) {
        Order order = find(orderUuid);
        order.getAnalysisResult().setMethodDescription(analysis.getMethodDescription());
        order.getAnalysisResult().setRemarks(analysis.getRemarks());
        order.getAnalysisResult().setResultCategory(analysis.getResultCategory());
        order.getAnalysisResult().setResultDescription(analysis.getResultDescription());
        order.getAnalysisResult().setEndDate(LocalDate.now());
        order.setState(Order.State.ANALYSIS_DONE);
        orderRepository.save(order);
        return "redirect:/order/" + orderUuid;
    }

    private Order find(String orderUuid) {
        Optional<Order> order = orderRepository.findById(orderUuid);
        return order.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }
}
