package ch.fhnw.digibp;

import ch.fhnw.digibp.client.Client;
import ch.fhnw.digibp.domain.AnalysisType;
import ch.fhnw.digibp.domain.Priority;
import ch.fhnw.digibp.domain.SampleType;
import ch.fhnw.digibp.order.BillingInformation;
import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.SampleRequirements;

public class TestDataFactory {
    public static final String ORDER_UUID = "0a5fa6c2-329f-45ee-9925-13aed405dfde";

    public static Order buildTestOrder() {
        Order order = new Order();
        order.setAnalysisType(AnalysisType.Sars_Cov_2);
        order.setClient(buildClient());
        order.setComment("please analyse this :)");
        order.setUuid(ORDER_UUID);
        order.setBillingInformation(buildBillingInformation());
        order.setSampleRequirements(buildSampleRequirements());
        return order;
    }

    public static Client buildClient() {
        Client client = new Client();
        client.setName("123");
        client.setEmail("123@123.ch");
        return client;
    }

    public static BillingInformation buildBillingInformation() {
        BillingInformation billingInformation = new BillingInformation();
        billingInformation.setPrice(106);
        return billingInformation;
    }

    public static SampleRequirements buildSampleRequirements() {
        SampleRequirements sampleRequirements = new SampleRequirements();
        sampleRequirements.setBiohazard(true);
        sampleRequirements.setSeverityOfMisClassification(Priority.MEDIUM);
        sampleRequirements.setSampleType(SampleType.SALIVA);
        return sampleRequirements;
    }
}
