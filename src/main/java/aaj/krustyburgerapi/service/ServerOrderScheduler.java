package aaj.krustyburgerapi.service;

import aaj.krustyburgerapi.dto.OrderStatus;
import aaj.krustyburgerapi.entity.Order;
import aaj.krustyburgerapi.entity.Server;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Qualifier("serverOrderScheduler")
@EnableScheduling
public class ServerOrderScheduler implements OrderScheduler {
    private final ConcurrentLinkedQueue<Server> queue = new ConcurrentLinkedQueue<>();

    private final ServerEntityService serverEntityService;

    private final OrderEntityService orderEntityService;

    @Autowired
    public ServerOrderScheduler(ServerEntityService serverEntityService, OrderEntityService orderEntityService) {
        this.serverEntityService = serverEntityService;
        this.orderEntityService = orderEntityService;
    }


    @Override
    public Order next(Order order) {
        Server server = queue.poll();
        queue.add(server);
        return order.setServer(server);
    }

    @PostConstruct
    private void postConstruct() {
        queue.addAll(serverEntityService.findAll().stream().filter(server -> server.getStatus().equals("ACTIVE")).limit(2).toList());
    }

    @Scheduled(fixedDelay = 15000L)
    private void switchToInQueue() {
        orderEntityService.findAll().stream().filter(Objects::nonNull).filter(order -> order.getStatus().equals(OrderStatus.READY_TO_SERVE.getName()))
                .forEach(order -> orderEntityService.save(order.setStatus(OrderStatus.IN_QUEUE_TO_SERVE.getName())));
    }

    @Scheduled(fixedDelay = 15000L)
    private void switchToNext() {
        orderEntityService.findAll().stream().filter(Objects::nonNull).filter(order -> order.getStatus().equals(OrderStatus.IN_QUEUE_TO_SERVE.getName()))
                .forEach(order -> orderEntityService.save(next(order)));
    }
}
