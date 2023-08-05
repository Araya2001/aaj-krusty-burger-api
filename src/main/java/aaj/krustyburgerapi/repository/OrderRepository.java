package aaj.krustyburgerapi.repository;

import aaj.krustyburgerapi.entity.Cook;
import aaj.krustyburgerapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
