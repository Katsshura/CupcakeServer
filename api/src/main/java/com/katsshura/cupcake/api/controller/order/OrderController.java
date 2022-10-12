package com.katsshura.cupcake.api.controller.order;

import com.katsshura.cupcake.core.dto.order.CreateOrderDTO;
import com.katsshura.cupcake.core.dto.order.OrderResponseDTO;
import com.katsshura.cupcake.core.services.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid CreateOrderDTO orderDTO) {
        log.debug("Received request to create order: [{}]", orderDTO);
        final var result = this.orderService.createOrder(orderDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        final var result = this.orderService.getOrder(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderResponseDTO>> getOrdersUser(@PathVariable Long userId,
                                                                @PageableDefault Pageable pageable) {
        final var result = this.orderService.getOrdersForUser(userId, pageable);
        return ResponseEntity.ok(result);
    }
}
