package ch.fhnw.digibp.client;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ch.fhnw.digibp.AbstractEntity;

@Entity
@Table(name = "client")
public class Client extends AbstractEntity {

    public static final String PREFIX = "client";

    @Id
    @Column
    private String name;
    @Column
    private String email;

    public Client() {
    }

    public Client(Map<String, Object> map) {
        name = getString(PREFIX + "." + "name", map);
        email = getString(PREFIX + "." + "email", map);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(PREFIX + "." + "name", name);
        map.put(PREFIX + "." + "email", email);
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
