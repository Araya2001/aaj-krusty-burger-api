package aaj.krustyburgerapi.controller;

import aaj.krustyburgerapi.dto.OrderStatus;
import aaj.krustyburgerapi.dto.ResponseEntityWrapperDTO;
import aaj.krustyburgerapi.entity.Customer;
import aaj.krustyburgerapi.entity.Order;
import aaj.krustyburgerapi.entity.OrderItem;
import aaj.krustyburgerapi.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@ControllerAdvice
@RequestMapping("/aaj-krusty-burger/api/v1")
@SuppressWarnings("Duplicates")
public class EntityController {
    private final OrderEntityService orderEntityService;
    private final CookEntityService cookEntityService;

    private final CustomerEntityService customerEntityService;

    private final OrderItemEntityService orderItemEntityService;

    private final ServerEntityService serverEntityService;


    @Autowired
    public EntityController(OrderEntityService orderEntityService, CookEntityService cookEntityService, CustomerEntityService customerEntityService,
                            OrderItemEntityService orderItemEntityService, ServerEntityService serverEntityService) {
        this.orderEntityService = orderEntityService;
        this.cookEntityService = cookEntityService;
        this.customerEntityService = customerEntityService;
        this.orderItemEntityService = orderItemEntityService;
        this.serverEntityService = serverEntityService;
    }

    @PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseEntityWrapperDTO<Order>> postOrder(@RequestBody Order order) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseEntityWrapperDTO<Order> responseEntityWrapperDTO = new ResponseEntityWrapperDTO<>();
        responseEntityWrapperDTO
                .setStatus(httpStatus.name())
                .setMessage("SUCCESSFUL REQUEST!!!");
        if (order.getCustomer() == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            responseEntityWrapperDTO
                    .setStatus("ERROR")
                    .setMessage("NO CUSTOMER DATA")
                    .setData(null);
        } else {
            if (order.getCustomer().getId() == null) {
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                responseEntityWrapperDTO
                        .setStatus("ERROR")
                        .setMessage("CUSTOMER DOESN'T EXIST, PLEASE CREATE A CUSTOMER")
                        .setData(null);
            } else {
                order.setStatus(OrderStatus.PENDING_ITEMS.getName());
                responseEntityWrapperDTO.setData(orderEntityService.save(order));
            }
        }


        return new ResponseEntity<>(responseEntityWrapperDTO, httpStatus);

    }

    @PostMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseEntityWrapperDTO<Customer>> postCustomer(@RequestBody Customer customer) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseEntityWrapperDTO<Customer> responseEntityWrapperDTO = new ResponseEntityWrapperDTO<>();
        responseEntityWrapperDTO
                .setStatus(httpStatus.name())
                .setMessage("SUCCESSFUL REQUEST!!!");
        if (customer.getName() == null || customer.getEmail() == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            responseEntityWrapperDTO
                    .setStatus("ERROR")
                    .setMessage("CUSTOMER DATA IS INCOMPLETE")
                    .setData(null);
        } else {
            responseEntityWrapperDTO.setData(customerEntityService.save(customer));
        }

        return new ResponseEntity<>(responseEntityWrapperDTO, httpStatus);

    }

    @PostMapping(value = "/order/item/all", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseEntityWrapperDTO<List<OrderItem>>> postOrder(@RequestBody List<OrderItem> orderItems) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseEntityWrapperDTO<List<OrderItem>> responseEntityWrapperDTO = new ResponseEntityWrapperDTO<>();
        responseEntityWrapperDTO
                .setStatus(httpStatus.name())
                .setMessage("SUCCESSFUL REQUEST!!!");

        if (orderItems.stream().anyMatch(orderItem -> orderItem.getOrder() == null)) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            responseEntityWrapperDTO
                    .setStatus("ERROR")
                    .setMessage("NO ORDER DATA IN ORDER ITEM")
                    .setData(null);
        } else {

            if (orderItems.stream().anyMatch(orderItem -> orderItem.getQuantity() == null)) {
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                responseEntityWrapperDTO
                        .setStatus("ERROR")
                        .setMessage("NO QUANTITY DATA IN ORDER ITEM")
                        .setData(null);
            } else {
                if (orderItems.stream().anyMatch(orderItem -> orderItem.getValueVat() == null || orderItem.getValueNoVat() == null)) {
                    httpStatus = HttpStatus.NOT_ACCEPTABLE;
                    responseEntityWrapperDTO
                            .setStatus("ERROR")
                            .setMessage("NO QUANTITY DATA IN ORDER ITEM")
                            .setData(null);
                } else {
                    responseEntityWrapperDTO
                            .setData(
                                    orderItemEntityService.saveAll(
                                            orderItems.stream().map(orderItem ->
                                                    orderItem.setOrder(orderEntityService.findById(orderItem.getOrder().getId()).orElse(null))).toList()
                                    )
                            );
                    orderItems
                            .stream()
                            .map(orderItem ->
                                    orderItem.getOrder().getId()).distinct().forEach(aLong ->
                                    orderEntityService.findById(aLong).ifPresent(order -> orderEntityService.save(order.setStatus(OrderStatus.READY_TO_COOK.getName())
                                            .setChargeValueVat(orderItems.stream().filter(orderItem -> orderItem.getOrder().getId().equals(order.getId()))
                                                    .mapToDouble(orderItem -> orderItem.getValueVat() * orderItem.getQuantity()).sum())
                                            .setChargeValueNoVat(orderItems.stream().filter(orderItem -> orderItem.getOrder().getId().equals(order.getId()))
                                                    .mapToDouble(orderItem -> orderItem.getValueNoVat() * orderItem.getQuantity()).sum()))));
                }
            }
        }
        return new ResponseEntity<>(responseEntityWrapperDTO, httpStatus);

    }

    @PostMapping(value = "/order/change/status", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseEntityWrapperDTO<Order>> changeStatusOrder(@RequestParam("id") Long idOrder, @RequestParam("status") String status) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseEntityWrapperDTO<Order> responseEntityWrapperDTO = new ResponseEntityWrapperDTO<>();
        responseEntityWrapperDTO
                .setStatus(httpStatus.name())
                .setMessage("SUCCESSFUL REQUEST!!!");
        Order order = orderEntityService.findById(idOrder).orElse(null);
        if (order == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            responseEntityWrapperDTO
                    .setStatus("ERROR")
                    .setMessage("NO ORDER WITH REQUESTED ID IS PRESENT")
                    .setData(null);
        } else {
            if (!OrderStatus.isPresent(status)) {
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                responseEntityWrapperDTO
                        .setStatus("ERROR")
                        .setMessage("REQUESTED STATUS DOESN'T EXIST")
                        .setData(null);
            } else {
                responseEntityWrapperDTO.setData(order.setStatus(status));
            }
        }

        return new ResponseEntity<>(responseEntityWrapperDTO, httpStatus);
    }

    @GetMapping(value = "/order/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseEntityWrapperDTO<List<Order>>> getPendingOrders(@RequestParam("id") Long id, @RequestParam("employeeType") String employeeType,
                                                                           @RequestParam("viewType") String viewType) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseEntityWrapperDTO<List<Order>> responseEntityWrapperDTO = new ResponseEntityWrapperDTO<>();
        responseEntityWrapperDTO
                .setStatus(httpStatus.name())
                .setMessage("SUCCESSFUL REQUEST!!!");
        if (employeeType.equals("cook")) {
            if (viewType.equals("single")) {
                responseEntityWrapperDTO.setData(orderEntityService.findAll().stream().filter(order -> order.getCook().getId().equals(id) && (order.getStatus().equals(OrderStatus.IN_QUEUE_TO_COOK.getName()))).limit(1).toList());
            } else if (viewType.equals("all")) {
                responseEntityWrapperDTO.setData(orderEntityService.findAll().stream().filter(order -> order.getCook().getId().equals(id) && (order.getStatus().equals(OrderStatus.IN_QUEUE_TO_COOK.getName()))).toList());
            }

        } else if (employeeType.equals("server")) {
            if (viewType.equals("single")) {
                responseEntityWrapperDTO.setData(orderEntityService.findAll().stream().filter(order -> order.getServer().getId().equals(id) && (order.getStatus().equals(OrderStatus.IN_QUEUE_TO_SERVE.getName()))).limit(1).toList());
            } else if (viewType.equals("all")) {
                responseEntityWrapperDTO.setData(orderEntityService.findAll().stream().filter(order -> order.getServer().getId().equals(id) && (order.getStatus().equals(OrderStatus.IN_QUEUE_TO_SERVE.getName()))).toList());
            }

        }
        return new ResponseEntity<>(responseEntityWrapperDTO, httpStatus);
    }


}
