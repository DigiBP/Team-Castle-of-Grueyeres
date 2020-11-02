package ch.fhnw.digibp.order;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ch.fhnw.digibp.AbstractEntity;

@Embeddable
public class BillingInformation extends AbstractEntity {
    @Column
    private Currency currency = Currency.getInstance("CHF");
    @Column
    private double price;

    public BillingInformation() {
    }

    public BillingInformation(Map<String, Object> map) {
        this.price = getDouble("billingInformation.price", map);
        loadCurrency(map);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "price", getPrice());
        if (getCurrency() != null) {
            map.put(prefix + "currency", getCurrency().getCurrencyCode());
        }
        return map;
    }

    public Map<String, Object> toMapWithPrefix() {
        return toMapWithPrefix("billingInformation.");
    }

    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("");
    }

    private void loadCurrency(Map<String, Object> map) {
        if (mapHasKey("billingInformation.currency", map)) {
            currency = Currency.getInstance((String) map.get("billingInformation.currency"));
        }
    }

    @Override
    public String toString() {
        return "BillingInformation{" +
                "currency=" + currency +
                ", price=" + price +
                '}';
    }
}
