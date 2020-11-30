package ch.fhnw.digibp.order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String>, OrderRepositoryCustom {
}
