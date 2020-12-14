package ch.fhnw.digibp.analysis;

import ch.fhnw.digibp.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnalysisController {

    private final AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/order/{uuid}/analysis")
    public String order(Model model, @PathVariable(name = "uuid") String orderUuid) {
        Order order = analysisService.find(orderUuid);
        if (order.getAnalysisResult() == null) {
            order.setAnalysisResult(new Analysis());
        }
        model.addAttribute("order", order);
        return "submitTestResult";
    }

    @PostMapping(value = "/order/{orderUuid}/analysis", params = "action=save")
    public String save_analysis(@ModelAttribute Order order, @PathVariable(name = "orderUuid") String orderUuid, Model model) {
        analysisService.save(orderUuid, order.getAnalysisResult());
        return "redirect:/orders";
    }

    @PostMapping(value = "/order/{orderUuid}/analysis", params = "action=confirm")
    public String confirm_analysis(@ModelAttribute Order order, @PathVariable(name = "orderUuid") String orderUuid, Model model) {
        analysisService.confirm(orderUuid, order.getValidation());
        return "redirect:/orders";
    }

    @PostMapping(value = "/order/{orderUuid}/analysis", params = "action=reject")
    public String reject_analysis(@PathVariable(name = "orderUuid") String orderUuid, Model model) {
        analysisService.reject(orderUuid);
        return "redirect:/orders";
    }


}
