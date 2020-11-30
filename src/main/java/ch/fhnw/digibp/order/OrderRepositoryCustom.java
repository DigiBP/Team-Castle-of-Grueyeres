package ch.fhnw.digibp.order;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface OrderRepositoryCustom {

    @Query("select o from Order o order by o.orderDate asc")
    List<Order> findAllSorted();
}
