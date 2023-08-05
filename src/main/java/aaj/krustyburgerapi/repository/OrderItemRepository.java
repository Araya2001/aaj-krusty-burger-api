package aaj.krustyburgerapi.repository;

import aaj.krustyburgerapi.entity.Order;
import aaj.krustyburgerapi.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {
}
