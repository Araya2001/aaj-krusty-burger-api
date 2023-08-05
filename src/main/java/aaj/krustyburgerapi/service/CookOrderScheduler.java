package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.dto.OrderStatus;
import aaj.krustyburgerapi.entity.Cook;
import aaj.krustyburgerapi.entity.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Qualifier("cookOrderScheduler")
@EnableScheduling
public class CookOrderScheduler implements OrderScheduler {
    private final ConcurrentLinkedQueue<Cook> queue = new ConcurrentLinkedQueue<>();

    private final CookEntityService cookEntityService;

    private final OrderEntityService orderEntityService;


    @Autowired
    public CookOrderScheduler(CookEntityService cookEntityService, OrderEntityService orderEntityService) {
        this.cookEntityService = cookEntityService;
        this.orderEntityService = orderEntityService;
    }

    @Override
    public Order next(Order order) {
        Cook cook = queue.poll();
        queue.add(cook);
        return order.setCook(cook);
    }

    @PostConstruct
    private void postConstruct() {
        queue.addAll(cookEntityService.findAll().stream().filter(cook -> cook.getStatus().equals("ACTIVE")).limit(3).toList());
    }

    @Scheduled(fixedDelay = 15000L)
    private void switchToInQueue() {
        orderEntityService.findAll().stream().filter(Objects::nonNull).filter(order -> order.getStatus().equals(OrderStatus.READY_TO_COOK.getName()))
                .forEach(order -> orderEntityService.save(order.setStatus(OrderStatus.IN_QUEUE_TO_COOK.getName())));
    }

    @Scheduled(fixedDelay = 15000L)
    private void switchToNext() {
        orderEntityService.findAll().stream().filter(Objects::nonNull).filter(order -> order.getStatus().equals(OrderStatus.IN_QUEUE_TO_COOK.getName()))
                .forEach(order -> orderEntityService.save(next(order)));
    }
}
