package ch.fhnw.digibp.analysis;

import ch.fhnw.digibp.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class AnalysisEndpoint {
    private final AnalysisService analysisService;

    @Autowired
    public AnalysisEndpoint(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/api/order/{orderUuid}/analysis")
    public void post(@PathVariable("orderUuid") String orderUuid, @RequestBody AnalysisDto analysisDto) {
        Order order = analysisService.find(orderUuid);
        if (order.getState() != Order.State.IN_ANALYSIS) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Order not in analysis state!");
        }
        Analysis analysis = new Analysis();
        analysis.setResultValue(analysisDto.resultValue);
        analysis.setRemarks(analysisDto.getRemarks());
        analysis.setResultDescription(analysisDto.getResultDescription());
        analysisService.save(orderUuid, analysis);
    }

    public static class AnalysisDto {
        private double resultValue;
        private String resultDescription;
        private String remarks;

        public double getResultValue() {
            return resultValue;
        }

        public void setResultValue(double resultValue) {
            this.resultValue = resultValue;
        }

        public String getResultDescription() {
            return resultDescription;
        }

        public void setResultDescription(String resultDescription) {
            this.resultDescription = resultDescription;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}
