package aaj.krustyburgerapi.dto;

import java.util.Arrays;

public enum OrderStatus {

    PENDING_ITEMS("PENDING_ITEMS"),

    READY_TO_COOK("READY_TO_COOK"),
    IN_QUEUE_TO_COOK("IN_QUEUE_TO_COOK"),
    READY_TO_SERVE("READY_TO_SERVE"),
    IN_QUEUE_TO_SERVE("IN_QUEUE_TO_SERVE"),
    SERVED("READY_TO_SERVE"),

    PAID("PAID");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Boolean isPresent(String name) {
        return Arrays.stream(OrderStatus.values()).anyMatch(orderStatus -> orderStatus.getName().equals(name));
    }
}
