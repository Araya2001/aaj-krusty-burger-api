package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.entity.Order;

public interface OrderScheduler {
    Order next(Order order);

}
