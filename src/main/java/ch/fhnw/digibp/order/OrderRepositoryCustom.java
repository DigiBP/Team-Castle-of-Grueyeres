package ch.fhnw.digibp.order;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepositoryCustom {

    @Query("select o from Order o order by o.orderDate asc")
    List<Order> findAllSorted();

    @Query("select o from Order o where o.uuid in :uuids order by o.orderDate asc")
    List<Order> findAllSorted(@Param("uuids") Collection<String> uuids);
}
