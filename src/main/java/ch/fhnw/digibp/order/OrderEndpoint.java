package ch.fhnw.digibp.order;

import java.util.Optional;

import ch.fhnw.digibp.client.Client;
import ch.fhnw.digibp.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class OrderEndpoint {

    private final OrderService orderService;
    private final ClientRepository clientRepository;

    @Autowired
    public OrderEndpoint(OrderService orderService, ClientRepository clientRepository) {
        this.orderService = orderService;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/api/order")
    public @ResponseBody
    Order post(@RequestBody Order order) {
        if (order.getClient() == null) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Client information missing");
        }
        order.setClient(find(order.getClient().getName()));
        return orderService.create(order);
    }

    @GetMapping("/api/order/{uuid}")
    public @ResponseBody
    Order get(@PathVariable("uuid") String uuid) {
        return orderService.load(uuid);
    }

    private Client find(String name) {
        Optional<Client> client = clientRepository.findById(name);
        return client.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND, "Unknown client!"));
    }

}
