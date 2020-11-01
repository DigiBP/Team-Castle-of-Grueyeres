package ch.fhnw.digibp;

import ch.fhnw.digibp.order.Order;

public class TestDataFactory {
    public static final String ORDER_UUID = "0a5fa6c2-329f-45ee-9925-13aed405dfde";

    public static Order buildTestOrder() {
        Order order = new Order();
        order.setAnalysis("sars_cov_2");
        order.setClientId("test");
        order.setComment("please analyse this :)");
        order.setUuid(ORDER_UUID);
        return order;
    }
}
